package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Getter
@Setter
public class ProductSku {

    private Product product;

    private Sku sku;

    public static ProductSku of(ProductEntity productEntity, SkuEntity skuEntity) {
        ProductSku productSku = new ProductSku();

        productSku.setProduct(Product.of(productEntity));
        productSku.setSku(Sku.of(skuEntity));

        return productSku;
    }
}
