package com.sommor.core.model.enricher;

import com.sommor.core.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/29
 */
@Getter
@Setter
public class EntityReferenceConfig extends TargetConfig {

    private String entityName;

    private String fieldBy;

    private String[] fields;

    private Class target;
}
