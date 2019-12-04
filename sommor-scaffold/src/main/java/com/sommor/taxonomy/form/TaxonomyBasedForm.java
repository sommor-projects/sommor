package com.sommor.taxonomy.form;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.mybatis.sql.field.type.Config;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import com.sommor.scaffold.utils.Converter;
import com.sommor.taxonomy.entity.SubjectBasedEntity;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.entity.TaxonomySubjectRelationEntity;
import com.sommor.taxonomy.model.TaxonomyRelationConfig;
import com.sommor.taxonomy.param.SubjectFormRenderParam;
import com.sommor.taxonomy.repository.TaxonomySubjectRepository;
import com.sommor.taxonomy.repository.TaxonomyRepository;
import com.sommor.view.FieldsetView;
import com.sommor.view.FormView;
import com.sommor.view.config.HiddenInput;
import com.sommor.view.form.EntityForm;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/3
 */
public class TaxonomyBasedForm<Entity extends SubjectBasedEntity> extends EntityForm<Entity> {

    @HiddenInput
    @NotNull
    @Getter @Setter
    private Integer typeId;

    @Getter @Setter
    private SubjectTaxonomySelectionField subjectTaxonomySelections = new SubjectTaxonomySelectionField();

    private Map<TaxonomyEntity, List<TaxonomyEntity>> selectedTaxonomiesByType;

    private TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);

    private TaxonomySubjectRepository taxonomySubjectRepository = ApplicationContextHolder.getBean(TaxonomySubjectRepository.class);

    private List<TaxonomyRelationConfig> parseTaxonomyRelationConfigs(Integer id) {
        TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);

        List<TaxonomyRelationConfig> list = new ArrayList<>();

        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(id);
        for (TaxonomyEntity entity : paths) {
            if (null != entity.getConfig()) {
                list.addAll(entity.getConfig().getList(TaxonomyRelationConfig.class));
            }
        }

        return list;
    }

    private TaxonomyEntity parseTypeTaxonomy(SubjectFormRenderParam param) {
        TaxonomyEntity typeEntity = null;
        if (null != this.typeId && this.typeId > 0) {
            typeEntity = taxonomyRepository.findById(this.typeId);
            if (null == typeEntity) {
                throw new ErrorCodeException(ErrorCode.of("subject.form.type.invalid", this.typeId));
            }
        } else {
            if (param.getTypeId() != null && param.getTypeId() > 0) {
                typeEntity = taxonomyRepository.findById(param.getTypeId());
                if (null == typeEntity) {
                    throw new ErrorCodeException(ErrorCode.of("subject.form.param.typeId.invalid", param.getTypeId()));
                }
            } else if (StringUtils.isNoneBlank(param.getType())) {
                typeEntity = taxonomyRepository.findByType(param.getType());
                if (null == typeEntity) {
                    throw new ErrorCodeException(ErrorCode.of("subject.form.param.type.invalid", param.getType()));
                }
            }
        }

        if (null == typeEntity) {
            throw new ErrorCodeException(ErrorCode.of("subject.form.type.required"));
        }

        return typeEntity;
    }

    @Override
    public void render(Entity entity, FormView formView, EntityFormRenderParam param) {
        super.render(entity, formView, param);

        SubjectFormRenderParam renderParam = (SubjectFormRenderParam) param;
        TaxonomyEntity typeEntity = parseTypeTaxonomy(renderParam);

        if (null == entity) {
            this.setTypeId(typeEntity.getId());
        }

        Config taxonomyConfig = typeEntity.getConfig();

        if (null != taxonomyConfig) {
            List<TaxonomyRelationConfig> taxonomyRelationConfigs = parseTaxonomyRelationConfigs(typeEntity.getId());
            if (CollectionUtils.isNotEmpty(taxonomyRelationConfigs)) {
                FieldsetView fieldsetView = formView.getFieldset("subjectTaxonomySelections");
                for (TaxonomyRelationConfig taxonomyRelationConfig : taxonomyRelationConfigs) {
                    TaxonomyEntity te = taxonomyRepository.findById(taxonomyRelationConfig.getTypeId());
                    TaxonomySelectView taxonomySelectView = new TaxonomySelectView();
                    taxonomySelectView.setName(te.getSlug());
                    taxonomySelectView.setTitle(te.getTitle());
                    taxonomySelectView.setTypeId(taxonomyRelationConfig.getTypeId());
                    taxonomySelectView.setTree(true);
                    if (Boolean.TRUE.equals(taxonomyRelationConfig.getMultiple())) {
                        taxonomySelectView.multiple();
                    }
                    if (Boolean.TRUE.equals(taxonomyRelationConfig.getRequired())) {
                        taxonomySelectView.getConstraints().required();
                    }

                    fieldsetView.addField(taxonomySelectView);
                }
            }
        }
    }

    @Override
    public void validate() {
        super.validate();

        List<TaxonomyRelationConfig> taxonomyRelationConfigs = parseTaxonomyRelationConfigs(this.getTypeId());
        Map<TaxonomyEntity, List<TaxonomyEntity>> selections = parseSubjectTaxonomySelections(this.subjectTaxonomySelections);

        for (TaxonomyRelationConfig config : taxonomyRelationConfigs) {
            TaxonomyEntity type = taxonomyRepository.findById(config.getTypeId());

            if (Boolean.TRUE.equals(config.getRequired())) {
                List<TaxonomyEntity> selection = selections.get(type);
                if (null == selection) {
                    throw new ErrorCodeException(ErrorCode.of("subject.taxonomy.select.required", type.getId(), type.getTitle()));
                }
            }
        }

        //TODO: 检验选择的和配置的是否能互相匹配，是否多选检验

        this.selectedTaxonomiesByType = selections;
    }

    private Map<TaxonomyEntity, List<TaxonomyEntity>> parseSubjectTaxonomySelections(SubjectTaxonomySelectionField subjectTaxonomySelections) {
        Map<TaxonomyEntity, List<TaxonomyEntity>> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : subjectTaxonomySelections.entrySet()) {
            TaxonomyEntity typeEntity = taxonomyRepository.findByType(entry.getKey());
            Set<Integer> selected = parseSelectedTaxonomyIds(entry.getValue());
            if (CollectionUtils.isNotEmpty(selected)) {
                List<TaxonomyEntity> list = selected.stream()
                        .map(id -> taxonomyRepository.findById(id))
                        .collect(Collectors.toList());
                map.put(typeEntity, list);
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
    public void fromEntity(Entity entity) {
        super.fromEntity(entity);

        List<TaxonomySubjectRelationEntity> relationEntities = taxonomySubjectRepository.findBySubject(entity.subject(), entity.getId());

        if (CollectionUtils.isNotEmpty(relationEntities)) {
            Map<String, Set<Integer>> selectionMap = new HashMap<>();
            for (TaxonomySubjectRelationEntity relationEntity : relationEntities) {
                TaxonomyEntity typeEntity = taxonomyRepository.findById(relationEntity.getTaxonomyId());
                if (typeEntity.getTypeId() > 0) {
                    typeEntity = taxonomyRepository.findById(typeEntity.getTypeId());
                }
                Set<Integer> set = selectionMap.computeIfAbsent(typeEntity.getSlug(), p -> new HashSet<>());
                set.add(relationEntity.getTaxonomyId());
            }
            this.subjectTaxonomySelections = new SubjectTaxonomySelectionField(selectionMap);
        }
    }

    @Override
    public void save(Entity entity, Entity originalEntity) {
        super.save(entity, originalEntity);

        String subject = entity.subject();
        Integer id = entity.getId();

        taxonomySubjectRepository.deleteBySubject(subject, id);

        if (MapUtils.isNotEmpty(this.selectedTaxonomiesByType)) {
            for (Map.Entry<TaxonomyEntity, List<TaxonomyEntity>> entry : this.selectedTaxonomiesByType.entrySet()) {
                TaxonomyEntity typeEntity = entry.getKey();

                for (TaxonomyEntity selected : entry.getValue()) {
                    TaxonomySubjectRelationEntity relationEntity = new TaxonomySubjectRelationEntity();
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
        }
    }
}
