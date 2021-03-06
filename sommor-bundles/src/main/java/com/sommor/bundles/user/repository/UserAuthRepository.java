package com.sommor.bundles.user.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.bundles.user.entity.AuthenticationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Mapper
public interface UserAuthRepository extends CurdRepository<AuthenticationEntity> {

}
