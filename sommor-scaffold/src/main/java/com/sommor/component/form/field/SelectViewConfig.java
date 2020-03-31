package com.sommor.component.form.field;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/16
 */
public class SelectViewConfig extends FormFieldConfig<SelectView> {

    @Getter
    @Setter
    private List<Option> options;

    public void addOption(Option option) {
        if (null == this.options) {
            this.options = new ArrayList<>();
        }
        this.options.add(option);
    }
}
