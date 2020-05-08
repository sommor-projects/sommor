package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.taxonomy.component.attribute.Attributes;
import com.sommor.bundles.taxonomy.component.attribute.AttributesField;
import com.sommor.core.model.Model;
import com.sommor.mybatis.entity.config.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Getter
@Setter
public class Product {

    private Long id;

    private Integer productType;

    private Long shopId;

    private Long spuId;

    private String catalog;

    private String qrCode;

    private String title;

    private String subTitle;

    private String taxonomy;

    @AttributesField
    private Attributes attributes;

    private String cover;

    private String description;

    public static Product of(ProductEntity entity) {
        Product product = new Product();
        Model.fillData(product, entity);
        return product;
    }
}
