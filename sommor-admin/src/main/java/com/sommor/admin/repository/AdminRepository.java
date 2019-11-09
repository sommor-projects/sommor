package com.sommor.admin.repository;

import com.sommor.admin.entity.TestEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/25
 */
@Mapper
public interface AdminRepository extends CurdRepository<TestEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<TestEntity> findBy(Long id, Long parentId, String type);

}
