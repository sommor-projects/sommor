package com.sommor.bundles.mall.order.repository;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.mybatis.repository.CurdRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Mapper
public interface OrderRepository extends CurdRepository<OrderEntity, Long> {
}
