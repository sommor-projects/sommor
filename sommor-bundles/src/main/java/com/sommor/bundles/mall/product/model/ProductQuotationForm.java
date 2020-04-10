package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeField;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeSelection;
import com.sommor.core.component.currency.CurrencySelectField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 商品报价
 *
 * @author yanguanwei@qq.com
 * @since 2020/2/6
 */

public class ProductQuotationForm {
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @NotNull
    private Integer productId;

    @Getter
    @Setter
    @NotNull
    private Integer shopId;

    @Getter
    @Setter
    @CurrencySelectField
    private String currency;

    @Getter
    @Setter
    @NotBlank
    private String amount;

    @Getter
    @Setter
    private Integer inventory;

    @Getter
    @Setter
    @TaxonomyAttributeField(entityName = SkuEntity.NAME)
    private TaxonomyAttributeSelection taxonomy;

}
