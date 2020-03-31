package com.sommor.bundle.mall.product.model;

import com.sommor.bundle.mall.product.entity.SkuEntity;
import com.sommor.bundle.mall.product.entity.SpuEntity;
import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.media.component.file.MediaFiles;
import com.sommor.bundle.media.component.file.MediaFilesField;
import com.sommor.bundle.qrcode.component.qrcode.QrCodeField;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationField;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationSelection;
import com.sommor.component.form.field.InputField;
import com.sommor.model.target.EntityForm;
import com.sommor.component.entity.select.EntitySelectField;
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
    private Integer shopId;

    @InputField(title = "标题")
    @NotBlank
    private String title;

    @InputField(title = "副标题")
    private String subTitle;

    @TaxonomyRelationField(entityName = SkuEntity.NAME)
    private TaxonomyRelationSelection taxonomy;

    @MediaFilesField(entity = SpuEntity.NAME, maxCount = 5, coverFieldName = "cover", title = "图片")
    private MediaFiles pictures;

    @QrCodeField
    private String qrCode;

    @InputField(title = "描述", style = "textarea")
    private String description;
}
