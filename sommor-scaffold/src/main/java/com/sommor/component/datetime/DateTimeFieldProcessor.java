package com.sommor.component.datetime;

import com.sommor.core.utils.Converter;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Implement
public class DateTimeFieldProcessor implements FieldFillProcessor<DateTimeFieldConfig> {

    @Override
    public Object processOnFieldFill(DateTimeFieldConfig config, FieldFillContext ctx) {
        Class type = ctx.getModelField().getType();
        Object value = ctx.getSourceModelFieldValue();

        if (type == String.class) {
            if (value instanceof Integer) {
                return Converter.convertDateTime((Integer) value);
            }
        }

        return null;
    }
}
