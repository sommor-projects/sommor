package com.sommor.bundles.outline.service.processor;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.Order;
import com.sommor.bundles.mall.order.service.extension.OrderPaidProcessor;
import com.sommor.bundles.outline.model.OutlineOrderCreateParam;
import com.sommor.bundles.outline.service.OutlineOrderService;
import com.sommor.extensibility.config.Implement;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Implement
public class OutlineOrderPaidProcessor implements OrderPaidProcessor {

    @Resource
    private OutlineOrderService outlineOrderService;

    @Override
    public void processOrderPaid(Order order) {
        outlineOrderService.createOutlineOrder(order);
    }
}
