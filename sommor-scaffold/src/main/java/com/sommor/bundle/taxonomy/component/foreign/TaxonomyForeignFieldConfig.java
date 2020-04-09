package com.sommor.bundle.taxonomy.component.foreign;

import com.sommor.model.config.TargetConfig;
import com.sommor.view.field.FieldConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/28
 */
public class TaxonomyForeignFieldConfig extends TargetConfig {

    @Getter
    @Setter
    private String taxonomy;

    @Getter
    @Setter
    private String type;

}
