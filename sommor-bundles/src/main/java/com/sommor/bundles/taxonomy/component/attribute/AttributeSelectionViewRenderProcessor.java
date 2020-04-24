package com.sommor.bundles.taxonomy.component.attribute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.bundles.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.bundles.taxonomy.entity.SubjectTaxonomyRelationEntity;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundles.taxonomy.repository.TaxonomySubjectRepository;
import com.sommor.core.component.form.FieldSaveContext;
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
public class AttributeSelectionViewRenderProcessor implements
        ViewRenderProcessor<AttributeSelectionConfig>,
        FieldFillProcessor<AttributeSelectionConfig>,
        FormFieldValidateProcessor<AttributeSelectionConfig>,
        FormFieldSavingProcessor<AttributeSelectionConfig> {

    private static final String TAXONOMY_ATTRIBUTE_FORM_KEY = "TAXONOMY_ATTRIBUTE_FORM_KEY";

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Resource
    private TaxonomySubjectRepository taxonomySubjectRepository;

    @Override
    public void processOnViewRender(AttributeSelectionConfig vc, ViewRenderContext ctx) {
        ViewTree viewTree = vc.getViewTree();

        String taxonomy = vc.getTaxonomy();
        String entityName = vc.getEntityName();

        List<TaxonomyAttributeSetting> taxonomyAttributeSettings = parseTaxonomyAttributeSettings(taxonomy, entityName);
        if (CollectionUtils.isNotEmpty(taxonomyAttributeSettings)) {

            String fullNamePrefix = (StringUtils.isNotBlank(vc.getPathName()) ? (vc.getPathName() + ".") : "") + ctx.getView().getName() + ".";
            for (TaxonomyAttributeSetting taxonomyAttributeSetting : taxonomyAttributeSettings) {
                TaxonomyEntity typeEntity = taxonomyRepository.findByType(taxonomyAttributeSetting.getType());

                TaxonomySelectFieldConfig taxonomySelectFieldConfig = new TaxonomySelectFieldConfig();

                String fieldName = typeEntity.getName();
                taxonomySelectFieldConfig.setName(fieldName);
                taxonomySelectFieldConfig.setPathName(fullNamePrefix + "attributes");
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

    private List<TaxonomyAttributeSetting> parseTaxonomyAttributeSettings(String taxonomy, String entityName) {
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
    public Object processOnFieldFill(AttributeSelectionConfig config, FieldFillContext ctx) {
        TaxonomyEntity taxonomyEntity = null;
        String taxonomy = config.getTaxonomy();

        if (StringUtils.isNotBlank(taxonomy)) {
            taxonomyEntity = taxonomyRepository.findByName(taxonomy, config.getEntityName());
        }

        if (null == taxonomyEntity) {
            throw new ErrorCodeException(ErrorCode.of("entity.taxonomy.invalid"));
        }


        AttributeSelection selection = new AttributeSelection();
        selection.setTaxonomy(taxonomyEntity.getName());

        if (StringUtils.isNotBlank(config.getAttributes())) {
            Map<String, Object> attributes = JSONObject.parseObject(config.getAttributes());
            selection.setAttributes(attributes);
        }

//        Map<Long, Map<Long, SubjectTaxonomyRelationEntity>> selections = parseSubjectTaxonomySelections(config.getEntityName(), config.getEntityId());
//        if (MapUtils.isNotEmpty(selections)) {
//            for (Map.Entry<Long, Map<Long, SubjectTaxonomyRelationEntity>> entry : selections.entrySet()) {
//                if (! entry.getValue().containsKey(taxonomy)) {
//                    TaxonomyEntity entity = taxonomyRepository.findById(entry.getKey());
//                    selection.addAttribute(entity.getName(), entry.getValue().keySet());
//                }
//            }
//        }

        return selection;
    }

    private Map<Long, Map<Long, SubjectTaxonomyRelationEntity>> parseSubjectTaxonomySelections(String entityName, Long entityId) {
        if (null == entityId || entityId == 0) {
            return Collections.emptyMap();
        }

        List<SubjectTaxonomyRelationEntity> relationEntities = taxonomySubjectRepository.findBySubject(entityName, entityId);
        if (CollectionUtils.isNotEmpty(relationEntities)) {
            Map<Long, Map<Long, SubjectTaxonomyRelationEntity>> selectionMap = new HashMap<>();
            for (SubjectTaxonomyRelationEntity relationEntity : relationEntities) {
                Long typeId = relationEntity.getTypeId() > 0 ? relationEntity.getTypeId() : relationEntity.getTaxonomyId();
                Map<Long, SubjectTaxonomyRelationEntity> map = selectionMap.computeIfAbsent(typeId, p -> new HashMap<>());
                map.put(relationEntity.getTaxonomyId(), relationEntity);
            }

            return selectionMap;
        }

        return Collections.emptyMap();
    }

    @Override
    public void processOnFormValidate(AttributeSelectionConfig config, FieldContext ctx) {
        AttributeSelection attributeSelection = ctx.getFieldValue();
        if (null == attributeSelection) {
            throw new ErrorCodeException(ErrorCode.of("entity.taxonomy.attribute.selection.required"));
        }

        if (StringUtils.isBlank(attributeSelection.getTaxonomy())) {
            throw new ErrorCodeException(ErrorCode.of("entity.taxonomy.required"));
        }

        String subject = config.getEntityName();
        List<TaxonomyAttributeSetting> taxonomyAttributeSettings = parseTaxonomyAttributeSettings(attributeSelection.getTaxonomy(), subject);
        Map<TaxonomyEntity, List<TaxonomyEntity>> selections = parseSubjectTaxonomySelections(attributeSelection);

        for (TaxonomyAttributeSetting setting : taxonomyAttributeSettings) {
            TaxonomyEntity type = taxonomyRepository.findByType(setting.getType());

            if (Boolean.TRUE.equals(setting.getRequired())) {
                List<TaxonomyEntity> selection = selections.get(type);
                if (null == selection) {
                    throw new ErrorCodeException(ErrorCode.of("entity.taxonomy.select.required", type.getName(), type.getTitle()));
                }
            }
        }

        ctx.addExt(attributeSelection);
        ctx.addExt(TAXONOMY_ATTRIBUTE_FORM_KEY, selections);
    }

    private Map<TaxonomyEntity, List<TaxonomyEntity>> parseSubjectTaxonomySelections(AttributeSelection attributeSelection) {
        Map<TaxonomyEntity, List<TaxonomyEntity>> map = new HashMap<>();

        if (MapUtils.isNotEmpty(attributeSelection.getAttributes())) {
            for (Map.Entry<String, Object> entry : attributeSelection.getAttributes().entrySet()) {
                String type = entry.getKey();
                TaxonomyEntity typeEntity = taxonomyRepository.findByType(type);
                Set<String> selected = parseSelectedTaxonomies(entry.getValue());
                if (CollectionUtils.isNotEmpty(selected)) {
                    List<TaxonomyEntity> list = selected.stream()
                            .map(name -> taxonomyRepository.findByName(name, type))
                            .collect(Collectors.toList());
                    map.put(typeEntity, list);
                }
            }
        }
        return map;
    }

    private Set<String> parseSelectedTaxonomies(Object selected) {
        Set<String> set = new HashSet<>();
        if (selected instanceof Collection) {
            for (Object id : (Collection) selected) {
                set.add(Converter.toString(id));
            }
        } else {
            set.add(Converter.toString(selected));
        }

        return set;
    }

    @Override
    public void processOnFormSaving(AttributeSelectionConfig config, FieldSaveContext ctx) {
        BaseEntity entity = ctx.getEntity();
        AttributeSelection attributeSelection = ctx.getFieldValue();
        entity.setFieldValue(config.getTaxonomyFieldName(), attributeSelection.getTaxonomy());
        entity.setFieldValue(config.getAttributesFieldName(), JSON.toJSONString(attributeSelection.getAttributes()));
    }

//    @Override
//    public void processOnFormSaved(TaxonomyAttributeConfig config, FieldSaveContext ctx) {
//        BaseEntity entity = ctx.getEntity();
//
//        String entityName = entity.definition().getSubjectName();
//        Long entityId = entity.getId();
//
//        Map<Long, Map<Long, SubjectTaxonomyRelationEntity>> originalSelectionMap = parseSubjectTaxonomySelections(entityName, entityId);
//
//        Map<TaxonomyEntity, List<TaxonomyEntity>> selectedTaxonomiesByType = ctx.getExt("subject_taxonomy_selections");
//
//        if (entity.hasField(config.getTaxonomyFieldName())) {
//            TaxonomyAttributeSelection taxonomyAttributeSelection = ctx.getExt(TaxonomyAttributeSelection.class);
//            TaxonomyEntity taxonomyEntity = taxonomyRepository.findByName(taxonomyAttributeSelection.getTaxonomy(), entityName);
//            TaxonomyEntity typeEntity = taxonomyEntity.isRoot() ? taxonomyEntity : taxonomyRepository.findByType(taxonomyEntity.getType());
//            selectedTaxonomiesByType.put(typeEntity, Lists.newArrayList(taxonomyEntity));
//        }
//
//        if (MapUtils.isNotEmpty(selectedTaxonomiesByType)) {
//            for (Map.Entry<TaxonomyEntity, List<TaxonomyEntity>> entry : selectedTaxonomiesByType.entrySet()) {
//                TaxonomyEntity typeEntity = entry.getKey();
//                Map<Long, SubjectTaxonomyRelationEntity> originalSelections = originalSelectionMap.get(typeEntity.getId());
//
//                for (TaxonomyEntity selected : entry.getValue()) {
//                    if (null == originalSelections || !originalSelections.containsKey(selected.getId())) {
//                        SubjectTaxonomyRelationEntity relationEntity = new SubjectTaxonomyRelationEntity();
//                        relationEntity.setSubject(entityName);
//                        relationEntity.setSubjectId(entityId);
//                        relationEntity.setTypeId(typeEntity.getId());
//                        relationEntity.setTaxonomyId(selected.getId());
//
//                        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(selected.getName(), selected.getType());
//                        if (paths.size() > 1) {
//                            relationEntity.setTaxonomyId1(paths.get(1).getId());
//                        }
//                        if (paths.size() > 2) {
//                            relationEntity.setTaxonomyId2(paths.get(2).getId());
//                        }
//                        if (paths.size() > 3) {
//                            relationEntity.setTaxonomyId3(paths.get(3).getId());
//                        }
//                        if (paths.size() > 4) {
//                            relationEntity.setTaxonomyId4(paths.get(4).getId());
//                        }
//                        if (paths.size() > 5) {
//                            relationEntity.setTaxonomyId5(paths.get(5).getId());
//                        }
//
//                        taxonomySubjectRepository.add(relationEntity);
//                    }
//                }
//
//                if (null != originalSelections) {
//                    Set<Long> selections = entry.getValue().stream().map(p -> p.getId()).collect(Collectors.toSet());
//                    List<Long> deleteIds = originalSelections.entrySet().stream()
//                            .filter(p -> !selections.contains(p.getKey()))
//                            .map(p->p.getValue().getId())
//                            .collect(Collectors.toList());
//
//                    if (CollectionUtils.isNotEmpty(deleteIds)) {
//                        taxonomySubjectRepository.deleteByIds(deleteIds);
//                    }
//                }
//
//            }
//        }
//    }
}
