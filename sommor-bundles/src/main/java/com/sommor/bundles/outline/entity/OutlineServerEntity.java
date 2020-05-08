package com.sommor.bundles.outline.entity;

import com.sommor.core.component.configurable.ConfigurableEntity;
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
@Table(value = "outline_servers", entity = OutlineServerEntity.NAME, softDelete = true)
public class OutlineServerEntity extends ConfigurableEntity<String> {

    public static final String NAME = "outline-server";

    public static final Integer STATUS_RUNNING = 1;
    public static final Integer STATUS_CLOSED = 2;

    @Column
    private String name;

    @Column
    private Long agencyUserId;

    @Column
    private String host;

    @Column
    private Integer port;

    @Column
    private String apiUrl;

    @Column
    private Long startTime;

    @Column
    private Long endTime;

    @Column
    private Integer status;

    @Column
    private String taxonomy;

    @Column
    private String attributes;

    @Column
    private Integer isDeleted;
}
