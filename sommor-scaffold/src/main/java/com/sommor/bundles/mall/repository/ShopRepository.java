package com.sommor.bundles.mall.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.bundles.mall.entity.ShopEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Mapper
public interface ShopRepository extends CurdRepository<ShopEntity> {

}
