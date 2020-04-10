package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.taxonomy.model.EntityTaxonomyFormParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class SkuFormParam extends EntityTaxonomyFormParam {

    @Getter
    @Setter
    private Integer shopId;

    @Getter
    @Setter
    @NotNull
    private Integer productId;

    @Getter
    @Setter
    private Integer distributedSkuId;
}
