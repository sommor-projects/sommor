package com.sommor.bundles.outline.entity;

import com.sommor.core.scaffold.entity.timed.TimedEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Getter
@Setter
@Table(value = "outline_access_keys", entityName = OutlineAccessKeyEntity.NAME, autoIncrement = false)
public class OutlineAccessKeyEntity extends TimedEntity<String> {

    public static final String NAME = "outlineAccessKey";

    @Column
    private String outlineServerId;

    @Column
    private String accessId;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private Integer port;

    @Column
    private String method;

    @Column
    private String accessUrl;

    @Column
    private Integer status;

    @Column
    private Long usageBytes;
}
