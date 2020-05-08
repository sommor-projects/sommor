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
@Table(value = "outline_order_access_keys", autoIncrement = false, softDelete = true)
public class OutlineOrderAccessKeyEntity extends TimedEntity<String> {

    @Column
    private Long orderId;

    @Column
    private Long buyerId;

    @Column
    private String outlineServerId;
}
