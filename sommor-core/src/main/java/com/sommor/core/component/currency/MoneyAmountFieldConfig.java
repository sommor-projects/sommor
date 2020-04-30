package com.sommor.core.component.currency;

import com.sommor.core.component.form.field.FormFieldConfig;
import com.sommor.core.component.form.field.InputView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
public class MoneyAmountFieldConfig extends FormFieldConfig<InputView> {

    @Getter
    @Setter
    private String currency;

}
