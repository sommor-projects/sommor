package com.sommor.core.component.datetime;

import com.sommor.core.component.form.field.FormFieldView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
public class DateTimeRangeView extends FormFieldView {

    @Getter
    @Setter
    private String format;

    public DateTimeRangeView() {
        super("date-time-range");
    }
}
