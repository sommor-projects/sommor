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
 * @since 2019/12/3
 */
@Getter
@Setter
public class ShopForm extends EntityForm {

    @SubjectSelectField(title = "卖家", subject = "user", entityTitleName = "userName")
    @NotNull
    private Integer userId;

    @TextField(title = "名称")
    @NotBlank
    private String title;

    @TextField(title = "描述")
    private String description;

    @TextField
    private String address;

    private SubjectTaxonomy taxonomy;

    @Getter @Setter
    @MediaFilesField(title = "Logo")
    private MediaFiles logo;
}
