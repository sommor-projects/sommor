package com.sommor.bundles.mall.product.model;

import com.sommor.core.component.conditional.Conditional;
import com.sommor.core.scaffold.param.EntityQueryParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class SkuQueryParam extends EntityQueryParam {

    @Getter
    @Setter
    @NotNull
    @Conditional
    private Integer shopId;

    @Getter
    @Setter
    @NotNull
    @Conditional
    private Integer spuId;
}
