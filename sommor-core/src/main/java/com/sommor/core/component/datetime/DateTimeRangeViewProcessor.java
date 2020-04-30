package com.sommor.core.component.datetime;

import com.sommor.core.component.form.FieldSaveContext;
import com.sommor.core.component.form.extension.FormFieldSavingProcessor;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.core.utils.DateTimeUtil;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.BaseEntity;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/25
 */
@Implement
public class DateTimeRangeViewProcessor implements
        FieldFillProcessor<DateTimeRangeFieldConfig> ,
        FormFieldSavingProcessor<DateTimeRangeFieldConfig> {

    @Override
    public Object processOnFieldFill(DateTimeRangeFieldConfig config, FieldFillContext ctx) {
        if (ctx.getModelField().getType() == String[].class) {
            if (null != config.getStartTime() && null != config.getEndTime()) {
                return new String[] {
                        DateTimeUtil.formatDate(config.getStartTime()),
                        DateTimeUtil.formatDate(config.getEndTime())
                };
            }
        }

        return null;
    }

    @Override
    public void processOnFormSaving(DateTimeRangeFieldConfig config, FieldSaveContext ctx) {
        BaseEntity<?> baseEntity = ctx.getEntity();
        Object v = ctx.getFieldValue();
        if (null != v && v.getClass() == String[].class) {
            String[] a = (String[]) v;
            String startTime = a[0];
            if (startTime.indexOf('T') > 0) {
                startTime = startTime.split("T")[0];
            } else if (startTime.indexOf(" ") > 0) {
                startTime = startTime.split(" ")[0];
            }
            String endTime = a[1];
            if (endTime.indexOf('T') > 0) {
                endTime = endTime.split("T")[0];
            } else if (endTime.indexOf(" ") > 0) {
                endTime = endTime.split(" ")[0];
            }
            baseEntity.setFieldValue(config.getStartTimeFieldName(), DateTimeUtil.parseDate(startTime));
            baseEntity.setFieldValue(config.getEndTimeFieldName(), DateTimeUtil.parseDate(endTime));
        }
    }
}
