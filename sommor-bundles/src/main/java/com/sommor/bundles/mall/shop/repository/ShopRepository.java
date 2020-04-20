package com.sommor.bundles.mall.shop.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.mybatis.sql.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Mapper
public interface ShopRepository extends CurdRepository<ShopEntity, Long> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    ShopEntity findBySubTitle(String subTitle);
}
