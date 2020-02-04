package com.sommor.bundles.mall.entity;

import com.sommor.scaffold.entity.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table(value = "product_spu", subject = "spu")
@Getter
@Setter
public class SpuEntity extends ConfigurableEntity {

    @Column
    private Integer shopId;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private Integer taxonomy;

    @Column
    private String cover;

    @Column
    private String description;

}
