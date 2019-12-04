package com.sommor.mybatis.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.sql.field.type.Config;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Getter
@Setter
public class ConfigurableEntity extends TimedEntity {

    @Column
    private Config config;

}
