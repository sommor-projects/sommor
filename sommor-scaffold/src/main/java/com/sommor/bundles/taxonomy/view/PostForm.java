package com.sommor.bundles.taxonomy.view;

import com.sommor.bundles.media.fields.file.MediaFiles;
import com.sommor.scaffold.view.field.EntityForm;
import com.sommor.scaffold.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/22
 */
@Getter
@Setter
public class PostForm extends EntityForm {

    @TextField
    private String slug;

    @TextField
    @NotBlank
    private String title;

    @TextField
    @NotBlank
    private String subTitle;

    private MediaFiles pictures = new MediaFiles();

    @TextField
    @NotBlank
    private String description;
}
