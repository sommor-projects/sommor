package com.sommor.bundles.mall.order.service.extension;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.Order;
import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Extension(name = "订单付款后处理器")
public interface OrderPaidProcessor {

    void processOrderPaid(Order order);

}
