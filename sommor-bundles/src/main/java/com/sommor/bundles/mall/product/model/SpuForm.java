package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.entity.SpuEntity;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.media.component.file.MediaFiles;
import com.sommor.bundles.media.component.file.MediaFilesField;
import com.sommor.bundles.qrcode.component.qrcode.QrCodeField;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeField;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeSelection;
import com.sommor.core.component.form.field.InputField;
import com.sommor.core.model.EntityForm;
import com.sommor.core.component.entity.select.EntitySelectField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/22
 */
@Getter
@Setter
public class SpuForm extends EntityForm {

    @EntitySelectField(title = "所属店铺", entityName = ShopEntity.NAME)
    @NotNull
    private String shopId;

    @InputField(title = "标题")
    @NotBlank
    private String title;

    @InputField(title = "副标题")
    private String subTitle;

    @TaxonomyAttributeField(entityName = SkuEntity.NAME)
    private TaxonomyAttributeSelection taxonomy;

    @MediaFilesField(entity = SpuEntity.NAME, maxCount = 5, coverFieldName = "cover", title = "图片")
    private MediaFiles pictures;

    @QrCodeField
    private String qrCode;

    @InputField(title = "描述", style = "textarea")
    private String description;
}
