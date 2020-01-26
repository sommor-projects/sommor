package com.sommor.bundles.mall.repository;

import com.sommor.bundles.mall.entity.SpuEntity;
import com.sommor.mybatis.repository.CurdRepository;
import org.mapstruct.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/22
 */
@Mapper
public interface SpuRepository extends CurdRepository<SpuEntity> {
}
