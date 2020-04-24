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
@Table(value = "outline_orders", entityName = "outlineOrder", autoIncrement = false)
public class OutlineOrderEntity extends TimedEntity<Long> {

    @Column
    private Long userId;

    @Column
    private Integer startTime;

    @Column
    private Integer endTime;

    @Column
    private Integer accessKeyTotal;

    @Column
    private Integer accessKeyCount;
}
