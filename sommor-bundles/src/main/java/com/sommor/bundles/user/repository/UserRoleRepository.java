package com.sommor.bundles.user.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.bundles.user.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@Mapper
public interface UserRoleRepository extends CurdRepository<UserRoleEntity, Long> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<UserRoleEntity> findByUserId(Long userId);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    UserRoleEntity findByUserRole(Long userId, String role);
}
