package com.sommor.bundle.mall.product.entity;

import com.sommor.component.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table(value = "product_spu", entityName = SpuEntity.NAME)
@Getter
@Setter
public class SpuEntity extends ConfigurableEntity {

    public static final String NAME = "spu";

    @Column
    private Integer shopId;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private String qrCode;

    @Column
    private Integer taxonomyId;

    @Column
    private String cover;

    @Column
    private String description;

}
