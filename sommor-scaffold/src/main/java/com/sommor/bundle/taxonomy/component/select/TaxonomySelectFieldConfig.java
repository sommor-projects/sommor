package com.sommor.bundle.taxonomy.component.select;

import com.sommor.component.form.field.SelectView;
import com.sommor.component.form.field.FormFieldConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/14
 */
public class TaxonomySelectFieldConfig extends FormFieldConfig<SelectView> {

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Integer typeId;

    @Getter
    @Setter
    private String group;

    @Getter
    @Setter
    private Integer parentId;

    @Getter
    @Setter
    private Boolean multiple;

    @Getter
    @Setter
    private Boolean tree;

    @Getter
    @Setter
    private Boolean includeSelf;

    @Getter
    @Setter
    private Boolean includeRoot;
}
