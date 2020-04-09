package com.sommor.bundle.taxonomy.component.path;

import com.sommor.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.*;

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
