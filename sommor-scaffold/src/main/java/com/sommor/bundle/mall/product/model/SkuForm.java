package com.sommor.bundle.mall.product.model;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.mall.product.entity.SkuEntity;
import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.media.component.file.MediaFiles;
import com.sommor.bundle.media.component.file.MediaFilesField;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationField;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationSelection;
import com.sommor.component.currency.CurrencyEnum;
import com.sommor.component.currency.CurrencySelectField;
import com.sommor.component.currency.MoneyAmountField;
import com.sommor.component.form.EntityForm;
import com.sommor.component.form.field.InputField;
import com.sommor.model.enricher.EntityModelEnricher;
import com.sommor.component.entity.select.EntitySelectField;
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
    private Integer shopId;

    @Getter
    @Setter
    @NotNull
    @EntitySelectField(title = "商品", entityName = ProductEntity.NAME)
    private Integer productId;

    @Getter
    @Setter
    private Integer distributedSkuId;

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
    @TaxonomyRelationField(entityName = SkuEntity.NAME)
    private TaxonomyRelationSelection taxonomy;

    @Getter
    @Setter
    @MediaFilesField(entity = SkuEntity.NAME, maxCount = 5, coverFieldName = "cover", title = "图片")
    private MediaFiles pictures;

    @Getter
    @Setter
    private Integer warningInventory;
}
