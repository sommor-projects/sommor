package com.sommor.scaffold.fields.conditional;

import com.sommor.extensibility.config.Implement;
import com.sommor.scaffold.view.field.FieldInterceptor;
import com.sommor.scaffold.view.field.FieldQueryContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Implement
public class ConditionalFieldInterceptor implements FieldInterceptor {

    @Override
    public void interceptOnQuery(FieldQueryContext ctx) {
        Conditional conditional = ctx.getDefinition().getField().getAnnotation(Conditional.class);
        if (null == conditional) {
            return;
        }

        Object value = ctx.getFieldValue();
        if (! isEmpty(value)) {
            ctx.getQuery().where().condition().and(ctx.getDefinition().getName(), conditional.operator(), value);
        }
    }

    private boolean isEmpty(Object value) {
        if (value == null || (value instanceof String && StringUtils.isBlank((String) value))) {
            return true;
        }

        return false;
    }
}
