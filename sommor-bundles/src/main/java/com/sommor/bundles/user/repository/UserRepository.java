package com.sommor.bundles.user.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.bundles.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Mapper
public interface UserRepository extends CurdRepository<UserEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    UserEntity findByMobilePhone(String mobilePhone);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    UserEntity findByUserName(String userName);

}
