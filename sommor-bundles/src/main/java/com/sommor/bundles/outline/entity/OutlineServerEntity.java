package com.sommor.bundles.outline.entity;

import com.sommor.core.component.configurable.ConfigurableEntity;
import com.sommor.core.scaffold.entity.timed.TimedEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@Getter
@Setter
@Table(value = "outline_servers", entityName = "outlineServer")
public class OutlineServerEntity extends ConfigurableEntity<String> {

    @Column
    private String name;

    @Column
    private String host;

    @Column
    private Integer port;

    @Column
    private String apiUrl;

    @Column
    private Integer accessKeyCount;

    @Column
    private Integer startTime;

    @Column
    private Integer endTime;
}
