package com.sommor.bundles.mall.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Getter
@Setter
public class AdminOrderPaidParam {

    @NotNull
    private Long orderId;

}
