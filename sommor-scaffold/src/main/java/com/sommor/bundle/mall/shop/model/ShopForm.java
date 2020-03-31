package com.sommor.bundle.mall.shop.model;

import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.media.component.file.MediaFiles;
import com.sommor.bundle.media.component.file.MediaFilesField;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationField;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationSelection;
import com.sommor.bundle.user.entity.UserEntity;
import com.sommor.component.form.EntityForm;
import com.sommor.component.form.field.InputField;
import com.sommor.component.entity.select.EntitySelectField;
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

    @TaxonomyRelationField(entityName = ShopEntity.NAME)
    private TaxonomyRelationSelection taxonomy;

    @Getter @Setter
    @MediaFilesField(entity = ShopEntity.NAME, title = "Logo")
    private MediaFiles logo;
}
