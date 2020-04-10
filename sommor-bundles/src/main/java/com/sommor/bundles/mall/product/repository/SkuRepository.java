package com.sommor.bundles.mall.product.repository;

import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.entity.SpuEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Mapper
public interface SkuRepository extends CurdRepository<SkuEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    SpuEntity findBySubTitle(String subTitle);
}
