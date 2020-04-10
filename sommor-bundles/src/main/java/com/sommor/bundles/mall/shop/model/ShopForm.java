package com.sommor.bundles.mall.shop.model;

import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.media.component.file.MediaFiles;
import com.sommor.bundles.media.component.file.MediaFilesField;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeField;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeSelection;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.core.component.form.EntityForm;
import com.sommor.core.component.form.field.InputField;
import com.sommor.core.component.entity.select.EntitySelectField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/3
 */
@Getter
@Setter
public class ShopForm extends EntityForm {

    @EntitySelectField(title = "卖家", entityName = UserEntity.NAME)
    @NotNull
    private Integer userId;

    @InputField(title = "名称")
    @NotBlank
    private String title;

    @InputField(title = "英文名称")
    private String subTitle;

    @InputField(title = "描述")
    private String description;

    @InputField
    private String address;

    @TaxonomyAttributeField(entityName = ShopEntity.NAME)
    private TaxonomyAttributeSelection taxonomy;

    @Getter @Setter
    @MediaFilesField(entity = ShopEntity.NAME, title = "Logo")
    private MediaFiles logo;
}
