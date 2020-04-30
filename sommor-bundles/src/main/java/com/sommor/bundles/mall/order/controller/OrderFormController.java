package com.sommor.bundles.mall.order.controller;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.OrderForm;
import com.sommor.bundles.mall.order.model.OrderFormParam;
import com.sommor.bundles.mall.order.service.OrderFormService;
import com.sommor.core.component.form.FormController;
import com.sommor.core.component.form.FormService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@RestController
@RequestMapping(value = "/api/order")
public class OrderFormController extends FormController<OrderEntity, OrderForm, OrderFormParam> {

    public OrderFormController(OrderFormService orderFormService) {
        super(orderFormService);
    }

}
