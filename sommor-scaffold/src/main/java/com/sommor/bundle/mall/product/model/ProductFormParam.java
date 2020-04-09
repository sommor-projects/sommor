package com.sommor.bundle.mall.product.model;

import com.sommor.bundle.taxonomy.model.EntityTaxonomyFormParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class ProductFormParam extends EntityTaxonomyFormParam {

    @Getter
    @Setter
    private Integer shopId;

    @Getter
    @Setter
    private Integer spuId;

    @Getter
    @Setter
    private Integer catalogId;
}
