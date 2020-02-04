package com.sommor.scaffold.fields.currency;

import com.sommor.extensibility.config.Implement;
import com.sommor.core.view.SelectView;
import com.sommor.core.view.field.FieldProcessor;
import com.sommor.core.view.field.FieldRenderContext;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@Implement
public class CurrencySelectFieldProcessor implements FieldProcessor<CurrencySelectField> {

    @Override
    public void processOnFormRender(CurrencySelectField currencySelectField, FieldRenderContext ctx) {
        SelectView view = ctx.getFieldView();
        for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            view.addOption(currencyEnum.getTitle() + "("+currencyEnum.getName()+")", currencyEnum.getName());
        }
    }
}
