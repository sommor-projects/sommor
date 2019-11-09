package com.sommor.usercenter.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.usercenter.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Mapper
public interface UserRepository extends CurdRepository<UserEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    UserEntity findByTelephone(String telephone);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    UserEntity findByWeixinOpenid(String weixinOpenid);

}
