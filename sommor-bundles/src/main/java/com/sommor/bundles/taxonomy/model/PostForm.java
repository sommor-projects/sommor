package com.sommor.bundles.taxonomy.model;

import com.sommor.bundles.media.component.file.MediaFiles;
import com.sommor.bundles.media.component.file.MediaFilesField;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeField;
import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeSelection;
import com.sommor.bundles.taxonomy.entity.PostEntity;
import com.sommor.bundles.user.auth.AuthenticationHolder;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.core.component.form.EntityForm;
import com.sommor.core.component.form.field.InputField;
import com.sommor.core.model.Model;
import com.sommor.core.model.fill.OnModelFill;
import com.sommor.core.component.entity.select.EntitySelectField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/22
 */
@Getter
@Setter
public class PostForm extends EntityForm implements OnModelFill {

    @InputField
    private String slug;

    @InputField(title = "标题")
    @NotBlank
    private String title;

    @InputField(title = "副标题")
    @NotBlank
    private String subTitle;

    @MediaFilesField(entity = PostEntity.NAME, title = "图片")
    private MediaFiles pictures;

    @EntitySelectField(title = "发布者", entityName = UserEntity.NAME)
    @NotNull
    private Integer userId;

    @TaxonomyAttributeField(entityName = PostEntity.NAME)
    private TaxonomyAttributeSelection taxonomy;

    @InputField(title = "描述", style = "textarea")
    @NotBlank
    private String description;

    @Override
    public void onModelFill(Model model, Model sourceModel) {
        if (null == this.getUserId()) {
            this.setUserId(AuthenticationHolder.getAuthUser().getUserId());
        }
    }
}
