package com.sommor.bundles.taxonomy.component.select;

import com.sommor.core.component.form.field.SelectView;
import com.sommor.core.component.form.field.FormFieldConfig;
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
    private String group;

    @Getter
    @Setter
    private String parent;

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

    @Getter
    @Setter
    private Boolean isTaxonomyKey;
}
