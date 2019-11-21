package com.sommor.usercenter.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.usercenter.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@Mapper
public interface UserRoleRepository extends CurdRepository<UserRoleEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<UserRoleEntity> findByUserId(Integer userId);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    UserRoleEntity findByUserRole(Integer userId, String role);
}
