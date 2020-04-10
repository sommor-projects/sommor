package com.sommor.bundles.taxonomy.component.path;

import com.sommor.core.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/26
 */
public class TaxonomyPathFieldConfig extends TargetConfig {

    @Getter
    @Setter
    private String taxonomy;

    @Getter
    @Setter
    private String type;
}
