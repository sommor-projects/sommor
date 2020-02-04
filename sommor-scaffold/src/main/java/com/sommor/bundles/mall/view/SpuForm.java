package com.sommor.bundles.mall.view;

import com.sommor.bundles.media.fields.file.MediaFiles;
import com.sommor.bundles.media.fields.file.MediaFilesField;
import com.sommor.bundles.taxonomy.view.fields.subject.SubjectTaxonomy;
import com.sommor.core.view.field.EntityForm;
import com.sommor.scaffold.fields.subject.select.SubjectSelectField;
import com.sommor.core.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/22
 */
public class SpuForm extends EntityForm {

    @Getter
    @Setter
    @SubjectSelectField(title = "所属店铺", subject = "shop")
    @NotNull
    private Integer shopId;

    @Getter
    @Setter
    @TextField(title = "标题")
    @NotBlank
    private String title;

    @Getter
    @Setter
    @TextField(title = "副标题")
    private String subTitle;

    @Getter
    @Setter
    private SubjectTaxonomy taxonomy;

    @Getter
    @Setter
    @MediaFilesField(maxCount = 5, entityField = "cover", title = "图片")
    private MediaFiles pictures;

    @Getter
    @Setter
    @TextField(title = "描述", style = "textarea")
    private String description;
}
