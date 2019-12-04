package com.sommor.taxonomy.model;

import com.sommor.mybatis.sql.field.type.ConfigKey;
import com.sommor.taxonomy.form.TaxonomySelect;
import com.sommor.taxonomy.form.TaxonomySubjectSelect;
import com.sommor.view.FieldsetView;
import com.sommor.view.config.TextInput;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
@Getter
@Setter
@ConfigKey("trc")
public class TaxonomyRelationConfig {

    @TaxonomySubjectSelect
    private String subject;

    @TaxonomySelect(parentId = 0)
    private Integer typeId;

    @TextInput
    private Boolean multiple;

    @TextInput
    private Boolean required;

}
