package com.sommor.bundle.mall.product.model;

import com.sommor.component.conditional.Conditional;
import com.sommor.scaffold.param.EntityQueryParam;
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
