package com.sommor.component.configurable;

import com.sommor.mybatis.sql.field.type.Config;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
public class ConfigurableFieldConfig {

    @Getter
    @Setter
    private String key;

    @Getter
    @Setter
    private Config config;
}
