package com.sommor.component.currency;

import com.sommor.component.form.FieldSaveContext;
import com.sommor.component.form.extension.FormFieldSavingProcessor;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillProcessor;
import com.sommor.mybatis.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/9
 */
@Implement
public class MoneyAmountFieldProcessor implements FieldFillProcessor<MoneyAmountFieldConfig>, FormFieldSavingProcessor<MoneyAmountFieldConfig> {

    @Override
    public void processOnFormSaving(MoneyAmountFieldConfig config, FieldSaveContext ctx) {
        Money money = parseMoney(config, ctx.getFieldValue());
        if (null != money) {
            BaseEntity entity = ctx.getEntity();
            entity.setFieldValue(ctx.getModelField().getName(), (int) money.getCent());
        }
    }

    @Override
    public Object processOnFieldFill(MoneyAmountFieldConfig config, FieldFillContext ctx) {
        Object amount = ctx.getModelField().getValue();
        Money money = parseMoney(config, amount);

        if (null != money) {
            Class fieldType = ctx.getModelField().getType();
            if (fieldType == Integer.class) {
                return (int) money.getCent();
            } else if (fieldType == String.class) {
                return money.getAmountString();
            }
        }

        return null;
    }

    private Money parseMoney(MoneyAmountFieldConfig config, Object amount) {
        CurrencyEnum currencyEnum = CurrencyEnum.CNY;
        if (StringUtils.isNotBlank(config.getCurrency())) {
            currencyEnum = CurrencyEnum.valueOf(config.getName());
        }

        Money money = null;
        if (amount instanceof String) {
            money = Money.fromCommonUnit(Double.parseDouble((String) amount), currencyEnum);
        } else if (amount instanceof Integer) {
            money = Money.fromMiniUnit((long) amount, currencyEnum);
        }

        return money;
    }
}
