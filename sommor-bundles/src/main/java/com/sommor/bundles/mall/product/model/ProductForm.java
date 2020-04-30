package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.media.component.file.MediaFiles;
import com.sommor.bundles.media.component.file.MediaFilesField;
import com.sommor.bundles.qrcode.component.qrcode.QrCodeField;
import com.sommor.bundles.taxonomy.component.attribute.AttributeSelectionField;
import com.sommor.bundles.taxonomy.component.attribute.AttributeSelection;
import com.sommor.bundles.taxonomy.component.select.TaxonomySelectField;
import com.sommor.bundles.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.core.component.form.EntityForm;
import com.sommor.core.component.form.field.InputField;
import com.sommor.core.component.entity.select.EntitySelectField;
import com.sommor.core.view.model.ModelFieldViewRenderContext;
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
public class ProductForm {

    private Long id;

    private Integer productType;

    private Long spuId;

    @EntitySelectField(title = "所属店铺", entityName = ShopEntity.NAME)
    @NotNull
    private Long shopId;

    @InputField(title = "标题")
    @NotBlank
    private String title;

    @InputField(title = "副标题")
    private String subTitle;

    @AttributeSelectionField(entityName = "product")
    private AttributeSelection taxonomy;

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
