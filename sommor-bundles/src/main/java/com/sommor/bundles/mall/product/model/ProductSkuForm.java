package com.sommor.bundles.mall.product.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/22
 */
@Getter
@Setter
public class ProductSkuForm {

    private ProductForm product;

    private SkuForm sku;
}
