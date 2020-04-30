package com.sommor.bundles.mall.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@Getter
@Setter
public class OrderFormParam {

    @NotNull
    private Long productId;

    @NotNull
    private Long skuId;

}
