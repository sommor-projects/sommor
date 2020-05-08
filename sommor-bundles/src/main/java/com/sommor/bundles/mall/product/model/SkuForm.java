package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.media.component.file.MediaFiles;
import com.sommor.bundles.media.component.file.MediaFilesField;
import com.sommor.bundles.taxonomy.component.attribute.AttributeSelectionField;
import com.sommor.bundles.taxonomy.component.attribute.AttributeSelection;
import com.sommor.core.component.currency.CurrencyEnum;
import com.sommor.core.component.currency.CurrencySelectField;
import com.sommor.core.component.currency.MoneyAmountField;
import com.sommor.core.component.form.field.InputField;
import com.sommor.core.model.enricher.EntityReference;
import com.sommor.core.component.entity.select.EntitySelectField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Getter
@Setter
@EntityReference(entity = ProductEntity.NAME, byField = "productId")
public class SkuForm  {

    private Long id;

    @EntitySelectField(title = "店铺", entityName = ShopEntity.NAME)
    private Long shopId;

    @NotNull
    @EntitySelectField(title = "商品", entityName = ProductEntity.NAME)
    private Long productId;

    private Integer productType;

    private Long distributedSkuId;

    @InputField(title = "标题")
    private String title;

    @NotNull
    @MoneyAmountField(title = "售价（元）")
    private String price;

    @NotNull
    @MoneyAmountField(title = "成本价（元）")
    private String costPrice;

    @CurrencySelectField
    private String currency = CurrencyEnum.CNY.getName();

    @NotNull
    @InputField(title = "库存数")
    private Integer inventory;

    @AttributeSelectionField(entityName = SkuEntity.NAME)
    private AttributeSelection taxonomy;

    @MediaFilesField(entity = SkuEntity.NAME, maxCount = 5, coverFieldName = "cover", title = "图片")
    private MediaFiles pictures;

    private Integer warningInventory;
}
