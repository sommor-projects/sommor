package com.sommor.core.model.enricher;

import com.sommor.core.curd.CurdManager;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.Model;
import com.sommor.mybatis.entity.BaseEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/8
 */
@Implement
public class EntityModelEnrichProcessor implements ModelEnrichProcessor<EntityModelEnrichConfig> {

    @Override
    public void processOnModelEnrich(EntityModelEnrichConfig config, ModelEnrichContext ctx) {
        List<Model> sourceModels = ctx.getSourceModels();
        if (CollectionUtils.isEmpty(sourceModels)) {
            return;
        }

        String entityName = config.getEntityName();
        String entityIdFieldName = config.getEntityIdFieldName();

        Set<Integer> entityIds = ctx.getSourceModels().stream()
                .map(p->(Integer) p.getFieldValue(entityIdFieldName))
                .filter(Objects::nonNull)
                .filter(p -> p > 0)
                .collect(Collectors.toSet());

        List<BaseEntity> entities = CurdManager.getCurdRepository(entityName)
                .findByIds(entityIds);


        if (CollectionUtils.isNotEmpty(entities)) {
            Map<Integer, Model> entityModelMap = new HashMap<>();
            for (BaseEntity entity : entities) {
                Model entityModel = Model.of(entity);
                entityModelMap.put(entity.getId(), entityModel);
            }

            String[] entityFieldNames = config.getEntityFieldNames();
            for (Model model : sourceModels) {
                Integer entityId = model.getFieldValue(entityIdFieldName);
                if (null != entityId) {
                    Model entityModel = entityModelMap.get(entityId);
                    if (null != entityModel) {
                        model.extend(entityModel, entityFieldNames);
                    }
                }
            }
        }
    }
}
