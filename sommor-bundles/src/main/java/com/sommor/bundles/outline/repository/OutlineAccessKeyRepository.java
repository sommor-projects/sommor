package com.sommor.bundles.outline.repository;

import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Mapper
public interface OutlineAccessKeyRepository extends CurdRepository<OutlineAccessKeyEntity, String> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<OutlineAccessKeyEntity> findByServerId(String serverId);
}
