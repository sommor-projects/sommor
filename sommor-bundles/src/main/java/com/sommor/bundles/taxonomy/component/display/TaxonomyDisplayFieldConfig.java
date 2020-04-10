package com.sommor.bundles.taxonomy.component.display;

import com.sommor.core.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/28
 */
public class TaxonomyDisplayFieldConfig extends TargetConfig {

    @Setter
    @Getter
    private String entityName;

    @Setter
    @Getter
    private Integer entityId;

    @Setter
    @Getter
    private String type;

    @Setter
    @Getter
    private boolean displayPaths;

}
