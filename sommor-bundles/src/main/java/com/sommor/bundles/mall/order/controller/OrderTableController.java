package com.sommor.bundles.mall.order.controller;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.OrderTable;
import com.sommor.core.component.table.TableController;
import com.sommor.core.scaffold.param.EntityQueryParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/1
 */
@RestController
@RequestMapping(value = "/api/order")
public class OrderTableController extends TableController<OrderEntity, OrderTable, EntityQueryParam> {
}
