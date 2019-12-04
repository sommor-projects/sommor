package com.sommor.taxonomy.form;

import com.sommor.media.form.PicturesField;
import com.sommor.media.form.PicturesFieldConfig;
import com.sommor.media.service.MediaService;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import com.sommor.taxonomy.entity.PostEntity;
import com.sommor.view.config.HiddenInput;
import com.sommor.view.config.TextInput;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/22
 */
@Getter
@Setter
public class PostForm extends TaxonomyBasedForm<PostEntity> {

    @HiddenInput
    private Integer id;

    @TextInput
    private String slug;

    @TextInput
    @NotBlank
    private String title;

    @TextInput
    @NotBlank
    private String subTitle;

    private PicturesField pictures = new PicturesField();

    @TextInput
    @NotBlank
    private String description;
}
