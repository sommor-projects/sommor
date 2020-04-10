package com.sommor.bundles.taxonomy.component.foreign;

import com.sommor.core.model.config.TargetConfig;
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
