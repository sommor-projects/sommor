package com.sommor.core.component.currency;

import com.sommor.core.component.form.FieldSaveContext;
import com.sommor.core.component.form.extension.FormFieldSavingProcessor;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
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
            entity.setFieldValue(ctx.getModelField().getName(), money.getCent());
        }
    }

    @Override
    public Object processOnFieldFill(MoneyAmountFieldConfig config, FieldFillContext ctx) {
        Object amount = ctx.getSourceModelFieldValue();
        Money money = parseMoney(config, amount);

        if (null != money) {
            Class fieldType = ctx.getModelField().getType();
            if (fieldType == Integer.class) {
                return (int) money.getCent();
            } else if (fieldType == Long.class) {
                return money.getCent();
            } else if (fieldType == String.class) {
                return money.toString();
            }
        }

        return null;
    }

    private Money parseMoney(MoneyAmountFieldConfig config, Object amount) {
        CurrencyEnum currencyEnum = CurrencyEnum.CNY;
        if (StringUtils.isNotBlank(config.getCurrency())) {
            currencyEnum = CurrencyEnum.valueOf(config.getCurrency());
        }

        Money money = null;
        if (amount instanceof String) {
            money = Money.fromCommonUnit(Double.parseDouble((String) amount), currencyEnum);
        } else if (amount instanceof Integer) {
            money = Money.fromMiniUnit((long) amount, currencyEnum);
        } else if (amount instanceof Long) {
            money = Money.fromMiniUnit((long) amount, currencyEnum);
        }

        return money;
    }
}
