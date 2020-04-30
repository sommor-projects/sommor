package com.sommor.core.component.datetime;

import com.sommor.core.component.form.field.FormFieldConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Getter
@Setter
public class DateTimeRangeFieldConfig extends FormFieldConfig<DateTimeRangeView> {

    private String format;

    private Integer startTime;

    private Integer endTime;

    private String startTimeFieldName;

    private String endTimeFieldName;
}
