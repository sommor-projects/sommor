package com.sommor.bundles.outline.repository;

import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.mybatis.repository.CurdRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Mapper
public interface OutlineServerRepository extends CurdRepository<OutlineServerEntity, String> {
}
