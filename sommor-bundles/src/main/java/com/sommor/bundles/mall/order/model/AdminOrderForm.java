package com.sommor.bundles.mall.order.model;

import com.sommor.core.component.form.EntityForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Getter
@Setter
public class AdminOrderForm {

    private Long userId;

    private Long skuId;

    private Integer buyQuantity;

    private Boolean paid;

}
