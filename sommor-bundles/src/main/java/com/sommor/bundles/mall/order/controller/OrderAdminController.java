package com.sommor.bundles.mall.order.controller;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.AdminOrderForm;
import com.sommor.bundles.mall.order.model.OrderFormParam;
import com.sommor.bundles.mall.order.service.OrderFormService;
import com.sommor.bundles.mall.order.service.OrderService;
import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.component.form.FormController;
import com.sommor.core.component.form.action.Add;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@RestController
@RequestMapping(value = "/api/admin/order")
public class OrderAdminController extends FormController<OrderEntity, AdminOrderForm, OrderFormParam> {

    @Resource
    private OrderService orderService;

    public OrderAdminController(OrderFormService orderFormService) {
        super(orderFormService);
    }

    @Override
    @ApiOperation(value = "添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResponse<OrderEntity> addForm(@Validated({Add.class}) @RequestBody AdminOrderForm adminOrderForm) {
        ApiResponse<OrderEntity> apiResponse = super.addForm(adminOrderForm);

        if (Boolean.TRUE.equals(adminOrderForm.getPaid())) {
            orderService.paid(apiResponse.getResult().getId());
        }

        return apiResponse;
    }
}
