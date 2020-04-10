package com.sommor.core.component.entity.count;

import com.sommor.core.curd.CurdManager;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.repository.CurdRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
@Implement
public class EntityCountFieldProcessor implements FieldFillProcessor<EntityCountFieldConfig> {

    @Override
    public Object processOnFieldFill(EntityCountFieldConfig config, FieldFillContext ctx) {
        String entityIdFieldName = config.getEntityIdFieldName();
        if (StringUtils.isBlank(entityIdFieldName)) {
            entityIdFieldName = config.getEntityName() + "Id";
        }

        Integer id = ctx.getSourceModel().getFieldValue(entityIdFieldName);
        CurdRepository repository = CurdManager.getCurdRepository(config.getEntityName());
        Query query = new Query().where(entityIdFieldName, id);
        Integer count = repository.count(query);
        return count;
    }
}
