package com.sommor.bundle.mall.product.repository;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.mybatis.repository.CurdRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Mapper
public interface ProductRepository extends CurdRepository<ProductEntity> {
}
