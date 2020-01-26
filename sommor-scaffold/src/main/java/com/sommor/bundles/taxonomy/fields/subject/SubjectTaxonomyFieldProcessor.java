package com.sommor.bundles.taxonomy.fields.subject;

import com.google.common.collect.Lists;
import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.entity.SubjectTaxonomyRelationEntity;
import com.sommor.bundles.taxonomy.fields.taxonomy.select.TaxonomySelectView;
import com.sommor.bundles.taxonomy.model.TaxonomyRelationConfig;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundles.taxonomy.repository.TaxonomySubjectRepository;
import com.sommor.bundles.taxonomy.view.SubjectFormRenderParam;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.definition.EntityFieldDefinition;
import com.sommor.scaffold.view.field.FieldRenderContext;
import com.sommor.scaffold.view.field.FieldSaveContext;
import com.sommor.scaffold.utils.Converter;
import com.sommor.scaffold.view.*;
import com.sommor.scaffold.view.field.FieldContext;
import com.sommor.scaffold.view.field.FieldProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
@Implement
public class SubjectTaxonomyFieldProcessor implements FieldProcessor<SubjectTaxonomyField> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Resource
    private TaxonomySubjectRepository taxonomySubjectRepository;

    @Override
    public void processOnFormRender(SubjectTaxonomyField subjectTaxonomyField, FieldRenderContext ctx) {
        SubjectTaxonomy subjectTaxonomy = ctx.getFieldValue();
        FieldsetView view = ctx.getFieldView();

        List<TaxonomyRelationConfig> taxonomyRelationConfigs = parseTaxonomyRelationConfigs(subjectTaxonomy.getTypeId());
        if (CollectionUtils.isNotEmpty(taxonomyRelationConfigs)) {
            String fullNamePrefix = view.getFullName();
            for (TaxonomyRelationConfig taxonomyRelationConfig : taxonomyRelationConfigs) {
                TaxonomyEntity te = taxonomyRepository.findById(taxonomyRelationConfig.getTypeId());
                TaxonomySelectView taxonomySelectView = new TaxonomySelectView();

                String fieldName = te.getSlug();
                taxonomySelectView.setName(fieldName);
                taxonomySelectView.setFullName(fullNamePrefix + ".relations." + fieldName);
                taxonomySelectView.setStyle(te.getConfig().getString("fs"));
                taxonomySelectView.setTitle(te.getTitle());
                taxonomySelectView.setTypeId(taxonomyRelationConfig.getTypeId());
                taxonomySelectView.setTree(true);
                if (Boolean.TRUE.equals(taxonomyRelationConfig.getMultiple())) {
                    taxonomySelectView.multiple();
                }
                if (Boolean.TRUE.equals(taxonomyRelationConfig.getRequired())) {
                    taxonomySelectView.getConstraints().required();
                }

                view.addField(taxonomySelectView);
            }
        }
    }

    private List<TaxonomyRelationConfig> parseTaxonomyRelationConfigs(Integer id) {
        List<TaxonomyRelationConfig> list = new ArrayList<>();

        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(id);
        for (TaxonomyEntity entity : paths) {
            if (null != entity.getConfig()) {
                List<TaxonomyRelationConfig> relationConfigs = entity.getConfig().getList(TaxonomyRelationConfig.class);
                if (CollectionUtils.isNotEmpty(relationConfigs)) {
                    list.addAll(relationConfigs);
                }
            }
        }

        return list;
    }

    @Override
    public Object processOnFill(SubjectTaxonomyField config, FieldContext ctx) {
        Integer typeId = null;
        String type = config.type();
        BaseEntity entity = null;

        if (type.isEmpty()) {
            Object target = ctx.getData().getTarget();
            if (target instanceof SubjectFormRenderParam) {
                SubjectFormRenderParam param = (SubjectFormRenderParam) target;
                type = param.getType();
            } else if (target instanceof BaseEntity) {
                entity = (BaseEntity) target;
                typeId = entity.getFieldValue(ctx.getDefinition().getName());
            }
        }

        if (null == typeId) {
            if (StringUtils.isBlank(type)) {
                throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.type.empty"));
            }

            TaxonomyEntity typeEntity = taxonomyRepository.findByType(type);
            if (null == typeEntity) {
                throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.type.invalid", type));
            }

            typeId = typeEntity.getId();
        }


        SubjectTaxonomy subjectTaxonomy = new SubjectTaxonomy();
        subjectTaxonomy.setTypeId(typeId);

        Map<Integer, Set<Integer>> selections = parseSubjectTaxonomySelections(entity);
        if (MapUtils.isNotEmpty(selections)) {
            for (Map.Entry<Integer, Set<Integer>> entry : selections.entrySet()) {
                if (! entry.getValue().contains(typeId)) {
                    TaxonomyEntity taxonomyEntity = taxonomyRepository.findById(entry.getKey());
                    subjectTaxonomy.addRelation(taxonomyEntity.getSlug(), entry.getValue());
                }
            }
        }

        return subjectTaxonomy;
    }

    private Map<Integer, Set<Integer>> parseSubjectTaxonomySelections(BaseEntity entity) {
        if (null == entity || entity.isNew()) {
            return Collections.emptyMap();
        }

        String subjectName = entity.definition().getSubjectName();
        List<SubjectTaxonomyRelationEntity> relationEntities = taxonomySubjectRepository.findBySubject(subjectName, entity.getId());

        if (CollectionUtils.isNotEmpty(relationEntities)) {
            Map<Integer, Set<Integer>> selectionMap = new HashMap<>();
            for (SubjectTaxonomyRelationEntity relationEntity : relationEntities) {
                Integer typeId = relationEntity.getTypeId() > 0 ? relationEntity.getTypeId() : relationEntity.getTaxonomyId();
                Set<Integer> set = selectionMap.computeIfAbsent(typeId, p -> new HashSet<>());
                set.add(relationEntity.getTaxonomyId());
            }

            return selectionMap;
        }

        return Collections.emptyMap();
    }

    @Override
    public void processOnFormValidate(SubjectTaxonomyField subjectTaxonomyField, FieldContext ctx) {
        SubjectTaxonomy subjectTaxonomy = ctx.getFieldValue();
        if (null == subjectTaxonomy) {
            throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.required"));
        }

        if (subjectTaxonomy.getTypeId() == null || subjectTaxonomy.getTypeId() == 0) {
            throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.typeId.required"));
        }

        List<TaxonomyRelationConfig> taxonomyRelationConfigs = parseTaxonomyRelationConfigs(subjectTaxonomy.getTypeId());
        Map<TaxonomyEntity, List<TaxonomyEntity>> selections = parseSubjectTaxonomySelections(subjectTaxonomy);

        for (TaxonomyRelationConfig config : taxonomyRelationConfigs) {
            TaxonomyEntity type = taxonomyRepository.findById(config.getTypeId());

            if (Boolean.TRUE.equals(config.getRequired())) {
                List<TaxonomyEntity> selection = selections.get(type);
                if (null == selection) {
                    throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.select.required", type.getId(), type.getTitle()));
                }
            }
        }

        ctx.addExt("subject_taxonomy_selections", selections);
    }

    @Override
    public void processOnFormSaving(SubjectTaxonomyField subjectTaxonomyField, FieldSaveContext ctx) {
        BaseEntity entity = ctx.getEntity();
        EntityFieldDefinition efd = entity.definition().getFieldDefinition(ctx.getDefinition().getName());
        if (null == efd) {
            throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.entity.field.required", ctx.getDefinition().getName()));
        }

        SubjectTaxonomy subjectTaxonomy = ctx.getFieldValue();
        entity.setFieldValue(efd, subjectTaxonomy.getTypeId());
    }

    @Override
    public void processOnFormSaved(SubjectTaxonomyField subjectTaxonomyField, FieldSaveContext ctx) {
        BaseEntity entity = ctx.getEntity();

        String subject = entity.definition().getSubjectName();
        Integer id = entity.getId();

        Map<Integer, Set<Integer>> originalSelectionMap = parseSubjectTaxonomySelections(entity);

        Map<TaxonomyEntity, List<TaxonomyEntity>> selectedTaxonomiesByType = ctx.getExt("subject_taxonomy_selections");
        if (MapUtils.isNotEmpty(selectedTaxonomiesByType)) {
            for (Map.Entry<TaxonomyEntity, List<TaxonomyEntity>> entry : selectedTaxonomiesByType.entrySet()) {
                TaxonomyEntity typeEntity = entry.getKey();
                Set<Integer> originalSelections = originalSelectionMap.get(typeEntity.getId());

                for (TaxonomyEntity selected : entry.getValue()) {
                    if (null == originalSelections || ! originalSelections.contains(selected.getId())) {
                        SubjectTaxonomyRelationEntity relationEntity = new SubjectTaxonomyRelationEntity();
                        relationEntity.setSubject(subject);
                        relationEntity.setSubjectId(id);
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
                    Set<Integer> selections = entry.getValue().stream().map(p->p.getId()).collect(Collectors.toSet());
                    List<Integer> deleteIds = originalSelections.stream()
                            .filter(p -> ! selections.contains(p))
                            .collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(deleteIds)) {
                        taxonomySubjectRepository.deleteByIds(deleteIds);
                    }
                }

            }
        }
    }

    private Map<TaxonomyEntity, List<TaxonomyEntity>> parseSubjectTaxonomySelections(SubjectTaxonomy subjectTaxonomy) {
        Map<TaxonomyEntity, List<TaxonomyEntity>> map = new HashMap<>();

        TaxonomyEntity taxonomyEntity = taxonomyRepository.findById(subjectTaxonomy.getTypeId());
        TaxonomyEntity typeEntity = taxonomyEntity.getTypeId() > 0 ? taxonomyRepository.findById(taxonomyEntity.getTypeId()) : taxonomyEntity;
        map.put(typeEntity, Lists.newArrayList(taxonomyEntity));

        if (MapUtils.isNotEmpty(subjectTaxonomy.getRelations())) {
            for (Map.Entry<String, Object> entry : subjectTaxonomy.getRelations().entrySet()) {
                typeEntity = taxonomyRepository.findByType(entry.getKey());
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
}
