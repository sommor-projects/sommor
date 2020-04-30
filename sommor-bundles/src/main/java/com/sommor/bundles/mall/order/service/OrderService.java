package com.sommor.bundles.mall.order.service;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.Order;
import com.sommor.bundles.mall.order.repository.OrderRepository;
import com.sommor.bundles.mall.order.service.extension.OrderPaidProcessor;
import com.sommor.bundles.mall.order.service.extension.OrderShippedProcessor;
import com.sommor.core.curd.CurdService;
import com.sommor.core.generator.IdGenerator;
import com.sommor.core.utils.DateTimeUtil;
import com.sommor.extensibility.ExtensionExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Service
public class OrderService extends CurdService<OrderEntity, Long> {

    @Resource
    private IdGenerator orderIdGenerator;

    @Resource
    private OrderRepository orderRepository;

    private static final ExtensionExecutor<OrderPaidProcessor> orderPaidProcessor = ExtensionExecutor.of(OrderPaidProcessor.class);
    private static final ExtensionExecutor<OrderShippedProcessor> orderShippedProcessor = ExtensionExecutor.of(OrderShippedProcessor.class);

    @Override
    protected void onSaving(OrderEntity entity, OrderEntity originalEntity) {
        super.onSaving(entity, originalEntity);
        if (null == originalEntity) {
            entity.setId(orderIdGenerator.generateId());
        }
    }

    public Order findOrder(Long orderId) {
        OrderEntity orderEntity = this.findById(orderId);
        if (null != orderEntity) {
            return Order.of(orderEntity);
        }
        return null;
    }

    public void paid(Long orderId) {
        OrderEntity orderEntity = this.findById(orderId);
        if (null != orderEntity) {
            OrderEntity update = new OrderEntity();
            update.setId(orderId);
            update.setPayStatus(OrderEntity.PAY_STATUS_PAID);
            update.setPayTime(DateTimeUtil.now());
            this.save(update, orderEntity);

            OrderEntity paidOrderEntity = this.findById(orderId);
            Order order = Order.of(paidOrderEntity);
            orderPaidProcessor.run(ext -> ext.processOrderPaid(order));
        }
    }

    public void ship(Long orderId) {
        OrderEntity orderEntity = this.findById(orderId);
        if (null != orderEntity) {
            OrderEntity update = new OrderEntity();
            update.setId(orderId);
            update.setShippingStatus(OrderEntity.SHIPPING_STATUS_SHIPPED);
            update.setStatus(OrderEntity.STATUS_SUCCESS);
            update.setShipTime(DateTimeUtil.now());
            this.save(update, orderEntity);

            OrderEntity shippedOrderEntity = this.findById(orderId);
            Order order = Order.of(shippedOrderEntity);
            orderShippedProcessor.run(ext -> ext.processOrderShipped(order));
        }
    }
}
