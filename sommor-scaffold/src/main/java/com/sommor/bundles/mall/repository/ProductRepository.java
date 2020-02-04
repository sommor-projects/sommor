package com.sommor.bundles.mall.repository;

import com.sommor.bundles.mall.entity.ProductEntity;
import com.sommor.mybatis.repository.CurdRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Mapper
public interface ProductRepository extends CurdRepository<ProductEntity> {
}
