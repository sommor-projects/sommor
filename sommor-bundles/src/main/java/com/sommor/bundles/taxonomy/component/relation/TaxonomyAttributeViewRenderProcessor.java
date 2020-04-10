package com.sommor.bundles.taxonomy.component.relation;

import com.google.common.collect.Lists;
import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.bundles.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.bundles.taxonomy.entity.SubjectTaxonomyRelationEntity;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundles.taxonomy.repository.TaxonomySubjectRepository;
import com.sommor.core.component.form.FieldSaveContext;
import com.sommor.core.component.form.extension.FormFieldSavedProcessor;
import com.sommor.core.component.form.extension.FormFieldSavingProcessor;
import com.sommor.core.component.form.extension.FormFieldValidateProcessor;
import com.sommor.core.component.form.field.SelectView;
import com.sommor.core.utils.Converter;
import com.sommor.core.curd.query.FieldContext;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.core.view.ViewEngine;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;
import com.sommor.core.view.model.ViewTree;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/14
 */
@Implement
public class TaxonomyAttributeViewRenderProcessor implements
        ViewRenderProcessor<TaxonomyAttributeConfig>,
        FieldFillProcessor<TaxonomyAttributeConfig>,
        FormFieldValidateProcessor<TaxonomyAttributeConfig>,
        FormFieldSavingProcessor<TaxonomyAttributeConfig>,
        FormFieldSavedProcessor<TaxonomyAttributeConfig> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Resource
    private TaxonomySubjectRepository taxonomySubjectRepository;

    @Override
    public void processOnViewRender(TaxonomyAttributeConfig vc, ViewRenderContext ctx) {
        ViewTree viewTree = vc.getViewTree();

        String taxonomy = vc.getTaxonomy();
        String entityName = vc.getEntityName();

        List<TaxonomyAttributeSetting> taxonomyAttributeSettings = parseTaxonomyRelationSettings(taxonomy, entityName);
        if (CollectionUtils.isNotEmpty(taxonomyAttributeSettings)) {

            String fullNamePrefix = (StringUtils.isNotBlank(vc.getPathName()) ? (vc.getPathName() + ".") : "") + ctx.getView().getName() + ".";
            for (TaxonomyAttributeSetting taxonomyAttributeSetting : taxonomyAttributeSettings) {
                TaxonomyEntity typeEntity = taxonomyRepository.findByType(taxonomyAttributeSetting.getType());

                TaxonomySelectFieldConfig taxonomySelectFieldConfig = new TaxonomySelectFieldConfig();

                String fieldName = typeEntity.getName();
                taxonomySelectFieldConfig.setName(fieldName);
                taxonomySelectFieldConfig.setPathName(fullNamePrefix + "relations");
                taxonomySelectFieldConfig.setStyle(typeEntity.getConfig().getString("fs"));
                taxonomySelectFieldConfig.setTitle(typeEntity.getTitle());
                taxonomySelectFieldConfig.setType(taxonomyAttributeSetting.getType());
                taxonomySelectFieldConfig.setTree(true);
                if (Boolean.TRUE.equals(taxonomyAttributeSetting.getMultiple())) {
                    taxonomySelectFieldConfig.setMultiple(true);
                }
                if (Boolean.TRUE.equals(taxonomyAttributeSetting.getRequired())) {
                    taxonomySelectFieldConfig.constraint().required();
                }

                SelectView selectView = ViewEngine.render(taxonomySelectFieldConfig, ctx.getSourceMode());
                viewTree.addView(selectView);
            }
        }
    }

    private List<TaxonomyAttributeSetting> parseTaxonomyRelationSettings(String taxonomy, String entityName) {
        List<TaxonomyAttributeSetting> list = new ArrayList<>();

        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(taxonomy, entityName);
        for (TaxonomyEntity entity : paths) {
            if (null != entity.getConfig()) {
                List<TaxonomyAttributeSetting> attributeSettings = entity.getConfig().getList(TaxonomyAttributeSetting.class);
                if (CollectionUtils.isNotEmpty(attributeSettings)) {
                    for (TaxonomyAttributeSetting attributeSetting : attributeSettings) {
                        if (entityName.equals(attributeSetting.getSubject())) {
                            list.add(attributeSetting);
                        }
                    }
                }
            }
        }

        return list;
    }

    @Override
    public Object processOnFieldFill(TaxonomyAttributeConfig config, FieldFillContext ctx) {
        TaxonomyEntity taxonomyEntity = null;
        String taxonomy =config.getTaxonomy();

        if (StringUtils.isNotBlank(taxonomy)) {
            taxonomyEntity = taxonomyRepository.findByName(taxonomy, config.getEntityName());
        }

        if (null == taxonomyEntity) {
            throw new ErrorCodeException(ErrorCode.of("entity.taxonomy.invalid"));
        }


        TaxonomyAttributeSelection selection = new TaxonomyAttributeSelection();
        selection.setTaxonomy(taxonomyEntity.getName());

        Map<Integer, Map<Integer, SubjectTaxonomyRelationEntity>> selections = parseSubjectTaxonomySelections(config.getEntityName(), config.getEntityId());
        if (MapUtils.isNotEmpty(selections)) {
            for (Map.Entry<Integer, Map<Integer, SubjectTaxonomyRelationEntity>> entry : selections.entrySet()) {
                if (! entry.getValue().containsKey(taxonomy)) {
                    TaxonomyEntity entity = taxonomyRepository.findById(entry.getKey());
                    selection.addAttribute(entity.getName(), entry.getValue().keySet());
                }
            }
        }

        return selection;
    }

    private Map<Integer, Map<Integer, SubjectTaxonomyRelationEntity>> parseSubjectTaxonomySelections(String entityName, Integer entityId) {
        if (null == entityId || entityId == 0) {
            return Collections.emptyMap();
        }

        List<SubjectTaxonomyRelationEntity> relationEntities = taxonomySubjectRepository.findBySubject(entityName, entityId);
        if (CollectionUtils.isNotEmpty(relationEntities)) {
            Map<Integer, Map<Integer, SubjectTaxonomyRelationEntity>> selectionMap = new HashMap<>();
            for (SubjectTaxonomyRelationEntity relationEntity : relationEntities) {
                Integer typeId = relationEntity.getTypeId() > 0 ? relationEntity.getTypeId() : relationEntity.getTaxonomyId();
                Map<Integer, SubjectTaxonomyRelationEntity> map = selectionMap.computeIfAbsent(typeId, p -> new HashMap<>());
                map.put(relationEntity.getTaxonomyId(), relationEntity);
            }

            return selectionMap;
        }

        return Collections.emptyMap();
    }

    @Override
    public void processOnFormValidate(TaxonomyAttributeConfig config, FieldContext ctx) {
        TaxonomyAttributeSelection taxonomyAttributeSelection = ctx.getFieldValue();
        if (null == taxonomyAttributeSelection) {
            throw new ErrorCodeException(ErrorCode.of("entity.taxonomy.attribute.selection.required"));
        }

        if (StringUtils.isBlank(taxonomyAttributeSelection.getTaxonomy())) {
            throw new ErrorCodeException(ErrorCode.of("entity.taxonomy.required"));
        }

        String subject = config.getEntityName();
        List<TaxonomyAttributeSetting> taxonomyAttributeSettings = parseTaxonomyRelationSettings(taxonomyAttributeSelection.getTaxonomy(), subject);
        Map<TaxonomyEntity, List<TaxonomyEntity>> selections = parseSubjectTaxonomySelections(taxonomyAttributeSelection);

        for (TaxonomyAttributeSetting setting : taxonomyAttributeSettings) {
            TaxonomyEntity type = taxonomyRepository.findByType(setting.getType());

            if (Boolean.TRUE.equals(setting.getRequired())) {
                List<TaxonomyEntity> selection = selections.get(type);
                if (null == selection) {
                    throw new ErrorCodeException(ErrorCode.of("entity.taxonomy.select.required", type.getName(), type.getTitle()));
                }
            }
        }

        ctx.addExt(taxonomyAttributeSelection);
        ctx.addExt("subject_taxonomy_selections", selections);
    }

    private Map<TaxonomyEntity, List<TaxonomyEntity>> parseSubjectTaxonomySelections(TaxonomyAttributeSelection taxonomyAttributeSelection) {
        Map<TaxonomyEntity, List<TaxonomyEntity>> map = new HashMap<>();

        if (MapUtils.isNotEmpty(taxonomyAttributeSelection.getAttributes())) {
            for (Map.Entry<String, Object> entry : taxonomyAttributeSelection.getAttributes().entrySet()) {
                TaxonomyEntity typeEntity = taxonomyRepository.findByKey(entry.getKey());
                Set<Integer> selected = parseSelectedTaxonomyIds(entry.getValue());
                if (CollectionUtils.isNotEmpty(selected)) {
                    List<TaxonomyEntity> list = selected.stream()
                            .map(id -> taxonomyRepository.findById(id))
                            .collect(Collectors.toList());
                    map.put(typeEntity, list);
                }
            }
        }
        return map;
    }

    private Set<Integer> parseSelectedTaxonomyIds(Object selected) {
        Set<Integer> set = new HashSet<>();
        if (selected instanceof Collection) {
            for (Object id : (Collection) selected) {
                set.add(Converter.parseInt(id));
            }
        } else {
            set.add(Converter.parseInt(selected));
        }

        return set;
    }

    @Override
    public void processOnFormSaving(TaxonomyAttributeConfig config, FieldSaveContext ctx) {
        BaseEntity entity = ctx.getEntity();
        TaxonomyAttributeSelection taxonomyAttributeSelection = ctx.getFieldValue();
        entity.setFieldValue(config.getTaxonomyFieldName(), taxonomyAttributeSelection.getTaxonomy());
    }

    @Override
    public void processOnFormSaved(TaxonomyAttributeConfig config, FieldSaveContext ctx) {
        BaseEntity entity = ctx.getEntity();

        String entityName = entity.definition().getSubjectName();
        Integer entityId = entity.getId();

        Map<Integer, Map<Integer, SubjectTaxonomyRelationEntity>> originalSelectionMap = parseSubjectTaxonomySelections(entityName, entityId);

        Map<TaxonomyEntity, List<TaxonomyEntity>> selectedTaxonomiesByType = ctx.getExt("subject_taxonomy_selections");

        if (entity.hasField(config.getTaxonomyFieldName())) {
            TaxonomyAttributeSelection taxonomyAttributeSelection = ctx.getExt(TaxonomyAttributeSelection.class);
            TaxonomyEntity taxonomyEntity = taxonomyRepository.findByName(taxonomyAttributeSelection.getTaxonomy(), entityName);
            TaxonomyEntity typeEntity = taxonomyEntity.isRoot() ? taxonomyEntity : taxonomyRepository.findByType(taxonomyEntity.getType());
            selectedTaxonomiesByType.put(typeEntity, Lists.newArrayList(taxonomyEntity));
        }

        if (MapUtils.isNotEmpty(selectedTaxonomiesByType)) {
            for (Map.Entry<TaxonomyEntity, List<TaxonomyEntity>> entry : selectedTaxonomiesByType.entrySet()) {
                TaxonomyEntity typeEntity = entry.getKey();
                Map<Integer, SubjectTaxonomyRelationEntity> originalSelections = originalSelectionMap.get(typeEntity.getId());

                for (TaxonomyEntity selected : entry.getValue()) {
                    if (null == originalSelections || !originalSelections.containsKey(selected.getId())) {
                        SubjectTaxonomyRelationEntity relationEntity = new SubjectTaxonomyRelationEntity();
                        relationEntity.setSubject(entityName);
                        relationEntity.setSubjectId(entityId);
                        relationEntity.setTypeId(typeEntity.getId());
                        relationEntity.setTaxonomyId(selected.getId());

                        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(selected.getName(), selected.getType());
                        if (paths.size() > 1) {
                            relationEntity.setTaxonomyId1(paths.get(1).getId());
                        }
                        if (paths.size() > 2) {
                            relationEntity.setTaxonomyId2(paths.get(2).getId());
                        }
                        if (paths.size() > 3) {
                            relationEntity.setTaxonomyId3(paths.get(3).getId());
                        }
                        if (paths.size() > 4) {
                            relationEntity.setTaxonomyId4(paths.get(4).getId());
                        }
                        if (paths.size() > 5) {
                            relationEntity.setTaxonomyId5(paths.get(5).getId());
                        }

                        taxonomySubjectRepository.insert(relationEntity);
                    }
                }

                if (null != originalSelections) {
                    Set<Integer> selections = entry.getValue().stream().map(p -> p.getId()).collect(Collectors.toSet());
                    List<Integer> deleteIds = originalSelections.entrySet().stream()
                            .filter(p -> !selections.contains(p.getKey()))
                            .map(p->p.getValue().getId())
                            .collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(deleteIds)) {
                        taxonomySubjectRepository.deleteByIds(deleteIds);
                    }
                }

            }
        }
    }
}
