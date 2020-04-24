package com.sommor.bundles.outline.repository;

import com.sommor.bundles.outline.entity.OutlineOrderEntity;
import com.sommor.mybatis.repository.CurdRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Mapper
public interface OutlineOrderRepository extends CurdRepository<OutlineOrderEntity, Long> {
}
