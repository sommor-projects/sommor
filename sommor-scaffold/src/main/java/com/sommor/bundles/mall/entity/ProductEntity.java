package com.sommor.bundles.mall.entity;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.scaffold.entity.configurable.ConfigurableEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table(value = "products", subject = "product")
public class ProductEntity extends ConfigurableEntity {

    @Setter
    @Getter
    @Column
    private Integer shopId;

    @Setter
    @Getter
    @Column
    private Integer spuId;

    @Setter
    @Getter
    @Column
    private Integer catalogId;
}
