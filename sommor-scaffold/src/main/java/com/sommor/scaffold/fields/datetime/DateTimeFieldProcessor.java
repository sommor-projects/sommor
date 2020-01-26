package com.sommor.scaffold.fields.datetime;

import com.sommor.extensibility.config.Implement;
import com.sommor.scaffold.view.field.FieldContext;
import com.sommor.scaffold.view.field.FieldProcessor;
import com.sommor.scaffold.utils.Converter;

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
