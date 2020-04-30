package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.taxonomy.model.EntityTaxonomyFormParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class ProductFormParam extends EntityTaxonomyFormParam {

    @Getter
    @Setter
    private Long shopId;

    @Getter
    @Setter
    private Long spuId;

    @Getter
    @Setter
    private String catalog;
}
