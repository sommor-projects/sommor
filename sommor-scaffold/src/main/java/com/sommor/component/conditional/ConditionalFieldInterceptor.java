package com.sommor.component.conditional;

import com.sommor.component.form.FormFieldInterceptor;
import com.sommor.curd.query.FieldQueryContext;
import com.sommor.curd.query.FieldQueryInterceptor;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.FieldDefinition;
import com.sommor.model.define.FieldDefineInterceptor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Implement
public class ConditionalFieldInterceptor implements FormFieldInterceptor, FieldQueryInterceptor, FieldDefineInterceptor {

    @Override
    public void interceptOnFieldDefine(FieldDefinition fieldDefinition) {
        Conditional conditional = fieldDefinition.getField().getAnnotation(Conditional.class);
        if (null != conditional) {
            fieldDefinition.addExt(conditional);
        }
    }

    @Override
    public void interceptOnQuery(FieldQueryContext ctx) {
        Conditional conditional = ctx.getModelField().getExt(Conditional.class);
        if (null == conditional) {
            return;
        }

        Object value = ctx.getFieldValue();
        if (! isEmpty(value)) {
            ctx.getQuery().where().condition().and(ctx.getModelField().getName(), conditional.operator(), value);
        }
    }

    private boolean isEmpty(Object value) {
        if (value == null || (value instanceof String && StringUtils.isBlank((String) value))) {
            return true;
        }

        return false;
    }
}
