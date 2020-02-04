package com.sommor.bundles.taxonomy.model;

import com.sommor.mybatis.sql.field.type.ConfigKey;
import com.sommor.bundles.taxonomy.view.fields.taxonomy.select.TaxonomySelect;
import com.sommor.scaffold.fields.subject.name.SubjectNameSelectField;
import com.sommor.core.view.field.config.SwitchField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
@Getter
@Setter
@ConfigKey("trc")
public class TaxonomyRelationConfig {

    @NotBlank
    @SubjectNameSelectField(title = "关联的内容")
    private String subject;

    @NotNull
    @TaxonomySelect(parentId = 0, title = "关联的分类")
    private Integer typeId;

    @SwitchField(title = "多选")
    private Boolean multiple;

    @SwitchField(title = "必选")
    private Boolean required;
}
