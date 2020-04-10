package com.sommor.core.component.currency;

import com.sommor.core.component.form.field.SelectView;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@Implement
public class CurrencySelectFieldProcessor implements ViewRenderProcessor<CurrencySelectFieldConfig> {


    @Override
    public void processOnViewRender(CurrencySelectFieldConfig currencySelectFieldConfig, ViewRenderContext ctx) {
        SelectView view = ctx.getView();
        for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            view.addOption(currencyEnum.getTitle() + "("+currencyEnum.getName()+")", currencyEnum.getName());
        }
    }
}
