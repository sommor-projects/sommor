package com.sommor.core.model.enricher;

import com.google.common.collect.Lists;
import com.sommor.core.curd.CurdManager;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.Model;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.field.type.Array;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/8
 */
@Implement
public class EntityReferenceProcessor implements FieldFillProcessor<EntityReferenceConfig>, ModelEnrichProcessor<EntityReferenceConfig> {

    @Override
    public void processOnModelEnrich(EntityReferenceConfig config, ModelEnrichContext ctx) {
       Map<Object, Model> referencedEntities = reference(config, ctx.getSourceModels(), config.getTarget());

        String entityIdFieldName = config.getByField();
        String[] entityFieldNames = config.getFields();

        for (Model model : ctx.getSourceModels()) {
            Object entityId = model.getFieldValue(entityIdFieldName);
            if (! BaseEntity.isIdEmpty(entityId)) {
                Model entityModel = referencedEntities.get(entityId);
                if (null != entityModel) {
                    model.extend(entityModel, entityFieldNames);
                }
            }
        }
    }

    private Map<Object, Model> reference(EntityReferenceConfig config, List<Model> sourceModels, Class targetClass) {
        if (CollectionUtils.isEmpty(sourceModels)) {
            return Collections.emptyMap();
        }

        String entityName = config.getEntity();
        String byField = config.getByField();

        Set<Object> refFieldValues = sourceModels.stream()
                .map(p->(p.getFieldValue(byField)))
                .filter(Objects::nonNull)
                .filter(p -> ! BaseEntity.isIdEmpty(p))
                .collect(Collectors.toSet());

        if (CollectionUtils.isEmpty(refFieldValues)) {
            return Collections.emptyMap();
        }

        Query query = new Query()
                .where(config.getRefField(), new Array(refFieldValues));

        List<BaseEntity<?>> entities = CurdManager.getCurdRepository(entityName).find(query);
        Map<Object, Model> referencedModels = new HashMap<>();
        if (CollectionUtils.isNotEmpty(entities)) {
            for (BaseEntity<?> entity : entities) {
                Model entityModel = Model.of(entity);
                if (targetClass != Void.class) {
                    entityModel = Model.of(entityModel.to(targetClass));
                }
                referencedModels.put(entity.getId(), entityModel);
            }
        }
        return referencedModels;
    }

    @Override
    public Object processOnFieldFill(EntityReferenceConfig config, FieldFillContext ctx) {
        Model sourceModel = ctx.getSourceModel();

        Class targetClass = config.getTarget();
        if (Void.class == targetClass) {
            if (! (BaseEntity.class.isAssignableFrom(ctx.getModelField().getType()))) {
                targetClass = ctx.getModelField().getType();
            }
        }

        Map<Object, Model> referencedEntities = reference(config, Lists.newArrayList(sourceModel), targetClass);

        String entityIdFieldName = config.getByField();
        Object entityId = sourceModel.getFieldValue(entityIdFieldName);
        if (! BaseEntity.isIdEmpty(entityId)) {
            Model referencedModel = referencedEntities.get(entityId);
            return referencedModel.getTarget();
        }

        return null;
    }
}
