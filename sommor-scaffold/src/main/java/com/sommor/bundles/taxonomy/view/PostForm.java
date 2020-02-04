package com.sommor.bundles.taxonomy.view;

import com.sommor.bundles.media.fields.file.MediaFiles;
import com.sommor.bundles.media.fields.file.MediaFilesField;
import com.sommor.bundles.taxonomy.view.fields.subject.SubjectTaxonomy;
import com.sommor.bundles.user.auth.AuthenticationHolder;
import com.sommor.scaffold.fields.subject.select.SubjectSelectField;
import com.sommor.core.view.field.DataSource;
import com.sommor.core.view.field.EntityForm;
import com.sommor.core.view.field.config.TextField;
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
public class PostForm extends EntityForm {

    @TextField
    private String slug;

    @TextField(title = "标题")
    @NotBlank
    private String title;

    @TextField(title = "副标题")
    @NotBlank
    private String subTitle;

    @MediaFilesField(title = "图片")
    private MediaFiles pictures;

    @SubjectSelectField(title = "发布者", subject = "user", entityTitleName = "userName")
    @NotNull
    private Integer userId;

    private SubjectTaxonomy taxonomy;

    @TextField(title = "描述", style = "textarea")
    @NotBlank
    private String description;

    @Override
    public void onFill(DataSource targetData) {
        if (null == this.getUserId()) {
            this.setUserId(AuthenticationHolder.getAuthUser().getUserId());
        }
    }
}
