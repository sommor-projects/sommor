package com.sommor.bundle.taxonomy.component.relation;

import com.google.common.collect.Lists;
import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.bundle.taxonomy.entity.SubjectTaxonomyRelationEntity;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundle.taxonomy.repository.TaxonomySubjectRepository;
import com.sommor.component.form.FieldSaveContext;
import com.sommor.component.form.extension.FormFieldSavedProcessor;
import com.sommor.component.form.extension.FormFieldSavingProcessor;
import com.sommor.component.form.extension.FormFieldValidateProcessor;
import com.sommor.component.form.field.SelectView;
import com.sommor.core.utils.Converter;
import com.sommor.curd.query.FieldContext;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillProcessor;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.view.ViewEngine;
import com.sommor.view.context.ViewRenderContext;
import com.sommor.view.extension.ViewRenderProcessor;
import com.sommor.view.model.ViewTree;
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
public class TaxonomyRelationViewRenderProcessor implements
        ViewRenderProcessor<TaxonomyRelationConfig>,
        FieldFillProcessor<TaxonomyRelationConfig>,
        FormFieldValidateProcessor<TaxonomyRelationConfig>,
        FormFieldSavingProcessor<TaxonomyRelationConfig>,
        FormFieldSavedProcessor<TaxonomyRelationConfig> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Resource
    private TaxonomySubjectRepository taxonomySubjectRepository;

    @Override
    public void processOnViewRender(TaxonomyRelationConfig vc, ViewRenderContext ctx) {
        ViewTree viewTree = vc.getViewTree();

        Integer taxonomyId = vc.getTaxonomyId();
        String entityName = vc.getEntityName();

        List<TaxonomyRelationSetting> taxonomyRelationSettings = parseTaxonomyRelationSettings(taxonomyId, entityName);
        if (CollectionUtils.isNotEmpty(taxonomyRelationSettings)) {

            String fullNamePrefix = (StringUtils.isNotBlank(vc.getPathName()) ? (vc.getPathName() + ".") : "") + ctx.getView().getName() + ".";
            for (TaxonomyRelationSetting taxonomyRelationSetting : taxonomyRelationSettings) {
                TaxonomyEntity typeEntity = taxonomyRepository.findById(taxonomyRelationSetting.getTypeId());

                TaxonomySelectFieldConfig taxonomySelectFieldConfig = new TaxonomySelectFieldConfig();

                String fieldName = typeEntity.getName();
                taxonomySelectFieldConfig.setName(fieldName);
                taxonomySelectFieldConfig.setPathName(fullNamePrefix + "relations");
                taxonomySelectFieldConfig.setStyle(typeEntity.getConfig().getString("fs"));
                taxonomySelectFieldConfig.setTitle(typeEntity.getTitle());
                taxonomySelectFieldConfig.setTypeId(taxonomyRelationSetting.getTypeId());
                taxonomySelectFieldConfig.setTree(true);
                if (Boolean.TRUE.equals(taxonomyRelationSetting.getMultiple())) {
                    taxonomySelectFieldConfig.setMultiple(true);
                }
                if (Boolean.TRUE.equals(taxonomyRelationSetting.getRequired())) {
                    taxonomySelectFieldConfig.constraint().required();
                }

                SelectView selectView = ViewEngine.render(taxonomySelectFieldConfig, ctx.getSourceMode());
                viewTree.addView(selectView);
            }
        }
    }

    private List<TaxonomyRelationSetting> parseTaxonomyRelationSettings(Integer taxonomyId, String subject) {
        List<TaxonomyRelationSetting> list = new ArrayList<>();

        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(taxonomyId);
        for (TaxonomyEntity entity : paths) {
            if (null != entity.getConfig()) {
                List<TaxonomyRelationSetting> relationConfigs = entity.getConfig().getList(TaxonomyRelationSetting.class);
                if (CollectionUtils.isNotEmpty(relationConfigs)) {
                    for (TaxonomyRelationSetting relationConfig : relationConfigs) {
                        if (subject.equals(relationConfig.getSubject())) {
                            list.add(relationConfig);
                        }
                    }
                }
            }
        }

        return list;
    }

    @Override
    public Object processOnFieldFill(TaxonomyRelationConfig config, FieldFillContext ctx) {
        TaxonomyEntity taxonomyEntity = null;

        Integer taxonomyId = config.getTaxonomyId();
        if (null == taxonomyId) {
            if (StringUtils.isNotBlank(config.getTaxonomy())) {
                taxonomyEntity = taxonomyRepository.findByName(config.getTaxonomy());
            }
        } else {
            taxonomyEntity = taxonomyRepository.findById(taxonomyId);
        }

        if (null == taxonomyEntity) {
            throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.invalid"));
        }

        TaxonomyRelationSelection selection = new TaxonomyRelationSelection();
        selection.setTaxonomyId(taxonomyEntity.getId());

        Map<Integer, Map<Integer, SubjectTaxonomyRelationEntity>> selections = parseSubjectTaxonomySelections(config.getEntityName(), config.getEntityId());
        if (MapUtils.isNotEmpty(selections)) {
            for (Map.Entry<Integer, Map<Integer, SubjectTaxonomyRelationEntity>> entry : selections.entrySet()) {
                if (! entry.getValue().containsKey(taxonomyId)) {
                    TaxonomyEntity entity = taxonomyRepository.findById(entry.getKey());
                    selection.addRelation(entity.getName(), entry.getValue().keySet());
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
    public void processOnFormValidate(TaxonomyRelationConfig config, FieldContext ctx) {
        TaxonomyRelationSelection taxonomyRelationSelection = ctx.getFieldValue();
        if (null == taxonomyRelationSelection) {
            throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.required"));
        }

        if (taxonomyRelationSelection.getTaxonomyId() == null || taxonomyRelationSelection.getTaxonomyId() == 0) {
            throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.typeId.required"));
        }

        String subject = config.getEntityName();
        List<TaxonomyRelationSetting> taxonomyRelationSettings = parseTaxonomyRelationSettings(taxonomyRelationSelection.getTaxonomyId(), subject);
        Map<TaxonomyEntity, List<TaxonomyEntity>> selections = parseSubjectTaxonomySelections(taxonomyRelationSelection);

        for (TaxonomyRelationSetting setting : taxonomyRelationSettings) {
            TaxonomyEntity type = taxonomyRepository.findById(setting.getTypeId());

            if (Boolean.TRUE.equals(setting.getRequired())) {
                List<TaxonomyEntity> selection = selections.get(type);
                if (null == selection) {
                    throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.select.required", type.getId(), type.getTitle()));
                }
            }
        }

        ctx.addExt(taxonomyRelationSelection);
        ctx.addExt("subject_taxonomy_selections", selections);
    }

    private Map<TaxonomyEntity, List<TaxonomyEntity>> parseSubjectTaxonomySelections(TaxonomyRelationSelection taxonomyRelationSelection) {
        Map<TaxonomyEntity, List<TaxonomyEntity>> map = new HashMap<>();

        if (MapUtils.isNotEmpty(taxonomyRelationSelection.getRelations())) {
            for (Map.Entry<String, Object> entry : taxonomyRelationSelection.getRelations().entrySet()) {
                TaxonomyEntity typeEntity = taxonomyRepository.findByName(entry.getKey());
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
    public void processOnFormSaving(TaxonomyRelationConfig config, FieldSaveContext ctx) {
        BaseEntity entity = ctx.getEntity();
        TaxonomyRelationSelection taxonomyRelationSelection = ctx.getFieldValue();
        entity.setFieldValue(config.getTaxonomyIdFieldName(), taxonomyRelationSelection.getTaxonomyId());
    }

    @Override
    public void processOnFormSaved(TaxonomyRelationConfig config, FieldSaveContext ctx) {
        BaseEntity entity = ctx.getEntity();

        String entityName = entity.definition().getSubjectName();
        Integer entityId = entity.getId();

        Map<Integer, Map<Integer, SubjectTaxonomyRelationEntity>> originalSelectionMap = parseSubjectTaxonomySelections(entityName, entityId);

        Map<TaxonomyEntity, List<TaxonomyEntity>> selectedTaxonomiesByType = ctx.getExt("subject_taxonomy_selections");

        if (entity.hasField(config.getTaxonomyIdFieldName())) {
            TaxonomyRelationSelection taxonomyRelationSelection = ctx.getExt(TaxonomyRelationSelection.class);
            TaxonomyEntity taxonomyEntity = taxonomyRepository.findById(taxonomyRelationSelection.getTaxonomyId());
            TaxonomyEntity typeEntity = taxonomyEntity.getTypeId() > 0 ? taxonomyRepository.findById(taxonomyEntity.getTypeId()) : taxonomyEntity;
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

                        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(selected.getId());
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
