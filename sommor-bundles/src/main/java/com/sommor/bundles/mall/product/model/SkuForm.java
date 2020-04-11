package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.media.component.file.MediaFiles;
import com.sommor.bundles.media.component.file.MediaFilesField;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeField;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeSelection;
import com.sommor.core.component.currency.CurrencyEnum;
import com.sommor.core.component.currency.CurrencySelectField;
import com.sommor.core.component.currency.MoneyAmountField;
import com.sommor.core.component.form.EntityForm;
import com.sommor.core.component.form.field.InputField;
import com.sommor.core.model.enricher.EntityModelEnricher;
import com.sommor.core.component.entity.select.EntitySelectField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@EntityModelEnricher(entityName = ProductEntity.NAME)
public class SkuForm extends EntityForm {

    @Getter
    @Setter
    @EntitySelectField(title = "店铺", entityName = ShopEntity.NAME)
    private String shopId;

    @Getter
    @Setter
    @NotNull
    @EntitySelectField(title = "商品", entityName = ProductEntity.NAME)
    private String productId;

    @Getter
    @Setter
    private String distributedSkuId;

    @Getter
    @Setter
    @InputField(title = "标题")
    private String title;

    @Getter
    @Setter
    @NotNull
    @MoneyAmountField(title = "售价（元）")
    private String price;

    @Getter
    @Setter
    @NotNull
    @MoneyAmountField(title = "成本价（元）")
    private String costPrice;

    @Getter
    @Setter
    @CurrencySelectField
    private String currency = CurrencyEnum.CNY.getName();

    @Getter
    @Setter
    @NotNull
    @InputField(title = "库存数")
    private Integer inventory;

    @Getter
    @Setter
    @TaxonomyAttributeField(entityName = SkuEntity.NAME)
    private TaxonomyAttributeSelection taxonomy;

    @Getter
    @Setter
    @MediaFilesField(entity = SkuEntity.NAME, maxCount = 5, coverFieldName = "cover", title = "图片")
    private MediaFiles pictures;

    @Getter
    @Setter
    private Integer warningInventory;
}
