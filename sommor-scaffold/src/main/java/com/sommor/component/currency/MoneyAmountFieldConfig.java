package com.sommor.component.currency;

import com.sommor.component.form.field.InputView;
import com.sommor.view.field.FieldConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
public class MoneyAmountFieldConfig extends FieldConfig<InputView> {

    @Getter
    @Setter
    private String currency;

}
