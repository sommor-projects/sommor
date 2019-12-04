package com.sommor.mybatis.entity;

import com.sommor.mybatis.entity.config.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Getter
@Setter
public class TimedEntity extends BaseEntity {

    @Column
    private Integer updateTime;

    @Column
    private Integer createTime;
}
