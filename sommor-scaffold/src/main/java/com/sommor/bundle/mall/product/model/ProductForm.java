package com.sommor.bundle.mall.product.model;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.media.component.file.MediaFiles;
import com.sommor.bundle.media.component.file.MediaFilesField;
import com.sommor.bundle.qrcode.component.qrcode.QrCodeField;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyAttributeField;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyAttributeSelection;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectField;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.component.form.EntityForm;
import com.sommor.component.form.field.InputField;
import com.sommor.component.entity.select.EntitySelectField;
import com.sommor.view.model.ModelFieldViewRenderContext;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Setter
@Getter
public class ProductForm extends EntityForm {

    @NotNull
    private Integer productType;

    private Integer spuId;

    @EntitySelectField(title = "所属店铺", entityName = ShopEntity.NAME)
    @NotNull
    private Integer shopId;

    @InputField(title = "标题")
    @NotBlank
    private String title;

    @InputField(title = "副标题")
    private String subTitle;

    @TaxonomyAttributeField(entityName = "product")
    private TaxonomyAttributeSelection taxonomy;

    @MediaFilesField(entity = ProductEntity.NAME, maxCount = 5, coverFieldName = "cover", title = "图片")
    private MediaFiles pictures;

    @QrCodeField
    private String qrCode;

    @InputField(title = "描述", style = "textarea")
    private String description;

    @TaxonomySelectField(type = "shop_catalog")
    private String catalog;

    public void renderCatalog(ModelFieldViewRenderContext ctx) {
        TaxonomySelectFieldConfig config = ctx.getFieldConfig();
        config.setGroup("shop_catalog_" + this.getShopId());
    }
}
