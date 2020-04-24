package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.taxonomy.model.EntityTaxonomyFormParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/23
 */
@Getter
@Setter
public class ProductSkuFormParam {

    private Long productId;

    private Long skuId;

    private String shopId;

    private String taxonomy;
}
