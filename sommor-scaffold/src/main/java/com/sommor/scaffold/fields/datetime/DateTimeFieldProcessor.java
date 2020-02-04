package com.sommor.scaffold.fields.datetime;

import com.sommor.extensibility.config.Implement;
import com.sommor.core.view.field.FieldContext;
import com.sommor.core.view.field.FieldProcessor;
import com.sommor.core.utils.Converter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Implement
public class DateTimeFieldProcessor implements FieldProcessor<DateTimeField> {

    @Override
    public Object processOnFill(DateTimeField dateTimeField, FieldContext ctx) {
        Class type = ctx.getDefinition().getField().getType();
        Object value = ctx.getFieldValue();

        if (type == String.class) {
            if (value instanceof Integer) {
                return Converter.convertDateTime((Integer) value);
            }
        }

        return null;
    }
}
