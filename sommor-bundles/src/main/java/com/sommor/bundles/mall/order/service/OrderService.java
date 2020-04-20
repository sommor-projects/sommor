package com.sommor.bundles.mall.order.service;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.core.curd.CurdService;
import org.springframework.stereotype.Service;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Service
public class OrderService extends CurdService<OrderEntity, Long> {
}
