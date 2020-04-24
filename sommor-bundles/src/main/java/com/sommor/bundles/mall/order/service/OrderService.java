package com.sommor.bundles.mall.order.service;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.AdminOrderForm;
import com.sommor.bundles.mall.order.model.Order;
import com.sommor.bundles.mall.order.service.extension.OrderPaidProcessor;
import com.sommor.bundles.mall.order.service.extension.OrderShippedProcessor;
import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.service.ProductService;
import com.sommor.bundles.mall.product.service.SkuService;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.mall.shop.service.ShopService;
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
    private SkuService skuService;

    @Resource
    private ProductService productService;

    @Resource
    private ShopService shopService;

    @Resource
    private IdGenerator orderIdGenerator;

    private static final ExtensionExecutor<OrderPaidProcessor> orderPaidProcessor = ExtensionExecutor.of(OrderPaidProcessor.class);
    private static final ExtensionExecutor<OrderShippedProcessor> orderShippedProcessor = ExtensionExecutor.of(OrderShippedProcessor.class);

    public Order findOrder(Long orderId) {
        OrderEntity orderEntity = this.findById(orderId);
        if (null != orderEntity) {
            return Order.of(orderEntity);
        }
        return null;
    }

    public OrderEntity adminCreate(AdminOrderForm adminOrderForm) {
        SkuEntity skuEntity = skuService.findById(adminOrderForm.getSkuId());
        ProductEntity productEntity = productService.findById(skuEntity.getProductId());
        ShopEntity shopEntity = shopService.findById(productEntity.getShopId());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderIdGenerator.generateId());
        orderEntity.setUserId(adminOrderForm.getUserId());
        orderEntity.setSkuId(skuEntity.getId());
        orderEntity.setProductId(productEntity.getId());
        orderEntity.setShopId(productEntity.getShopId());
        orderEntity.setProductTitle(productEntity.getTitle());
        orderEntity.setProductTaxonomy(productEntity.getTaxonomy());
        orderEntity.setProductAttributes(productEntity.getAttributes());
        orderEntity.setSkuAttributes(skuEntity.getAttributes());
        orderEntity.setSellerId(shopEntity.getUserId());
        orderEntity.setBuyQuantity(adminOrderForm.getBuyQuantity());
        orderEntity.setPayStatus(OrderEntity.PAY_STATUS_UNPAID);
        orderEntity.setStatus(OrderEntity.STATUS_CREATE);
        orderEntity.setShippingStatus(OrderEntity.SHIPPING_STATUS_UNSHIPPED);

        Long amount = skuEntity.getPrice() * adminOrderForm.getBuyQuantity();
        orderEntity.setAmount(amount);
        orderEntity.setCurrency(skuEntity.getCurrency());

        this.save(orderEntity);

        if (Boolean.TRUE.equals(adminOrderForm.getPaid())) {
            this.paid(orderEntity.getId());
        }

        return orderEntity;
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
