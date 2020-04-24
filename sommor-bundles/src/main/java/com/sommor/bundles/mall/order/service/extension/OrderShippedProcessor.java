package com.sommor.bundles.mall.order.service.extension;

import com.sommor.bundles.mall.order.model.Order;
import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Extension(name = "订单发货后处理器")
public interface OrderShippedProcessor {

    void processOrderShipped(Order order);

}
