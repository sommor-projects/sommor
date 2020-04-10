package com.sommor.bundles.taxonomy.component.style;


import com.sommor.core.component.form.field.FormFieldConfig;
import com.sommor.core.component.form.field.SelectView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
public class TaxonomyStyleFieldConfig extends FormFieldConfig<SelectView> {

    @Getter
    @Setter
    private String style;

}
