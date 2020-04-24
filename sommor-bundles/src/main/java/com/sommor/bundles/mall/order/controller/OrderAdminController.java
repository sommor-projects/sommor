package com.sommor.bundles.mall.order.controller;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.AdminOrderForm;
import com.sommor.bundles.mall.order.model.AdminOrderPaidParam;
import com.sommor.bundles.mall.order.service.OrderService;
import com.sommor.core.api.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@RestController
@RequestMapping(value = "/api/order/admin")
public class OrderAdminController {

    @Resource
    private OrderService orderService;

    @ApiOperation(value = "后端下单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResponse<OrderEntity> adminCreate(@Validated AdminOrderForm adminOrderForm) {
        OrderEntity orderEntity = orderService.adminCreate(adminOrderForm);
        return ApiResponse.success(orderEntity);
    }

    @ApiOperation(value = "后端付款")
    @RequestMapping(value = "/paid", method = RequestMethod.POST)
    public ApiResponse adminPaid(@Validated AdminOrderPaidParam adminOrderPaidParam) {
        orderService.paid(adminOrderPaidParam.getOrderId());
        return ApiResponse.success();
    }

}
