package com.sommor.bundles.mall.view;

import com.sommor.scaffold.fields.currency.CurrencyEnum;
import com.sommor.scaffold.fields.currency.CurrencySelectField;
import com.sommor.core.view.field.EntityForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class SkuForm extends EntityForm {

    @Getter
    @Setter
    private Integer productId;

    @Getter
    @Setter
    private Integer distributedSkuId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private Integer price;

    @Getter
    @Setter
    private Integer costPrice;

    @Getter
    @Setter
    @CurrencySelectField
    private String currency = CurrencyEnum.CNY.getName();

    @Getter
    @Setter
    private Integer inventory;

    @Getter
    @Setter
    private Integer warningInventory;
}
