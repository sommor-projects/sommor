package com.sommor.model.enricher;

import com.sommor.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/29
 */
public class EntityModelEnrichConfig extends TargetConfig {

    @Getter
    @Setter
    private String entityName;

    @Getter
    @Setter
    private String entityIdFieldName;

    @Getter
    @Setter
    private String[] entityFieldNames;
}
