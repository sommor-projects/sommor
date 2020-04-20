package com.sommor.bundles.mall.product.repository;

import com.sommor.bundles.mall.product.entity.SpuEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.mapstruct.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/22
 */
@Mapper
public interface SpuRepository extends CurdRepository<SpuEntity, Long> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    SpuEntity findBySubTitle(String subTitle);
}
