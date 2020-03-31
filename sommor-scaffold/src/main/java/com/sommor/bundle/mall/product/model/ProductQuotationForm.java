package com.sommor.bundle.mall.product.model;

import com.sommor.bundle.mall.product.entity.SkuEntity;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationField;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationSelection;
import com.sommor.component.currency.CurrencySelectField;
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
    @TaxonomyRelationField(entityName = SkuEntity.NAME)
    private TaxonomyRelationSelection taxonomy;

}
