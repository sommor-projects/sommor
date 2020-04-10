package com.sommor.core.component.entity.count;

import com.sommor.core.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/29
 */
public class EntityCountFieldConfig extends TargetConfig {

    @Getter
    @Setter
    String entityName;

    @Getter
    @Setter
    String entityIdFieldName;
}
