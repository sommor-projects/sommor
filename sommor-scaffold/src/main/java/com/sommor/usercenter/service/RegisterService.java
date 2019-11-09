package com.sommor.usercenter.service;

import com.sommor.api.response.Response;
import com.sommor.usercenter.entity.UserEntity;
import com.sommor.usercenter.model.UserStatus;
import com.sommor.usercenter.repository.UserRepository;
import com.sommor.usercenter.request.UserRegisterParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Service
public class RegisterService {

    @Resource
    private UserRepository userRepository;


    public Response<UserEntity> register(UserRegisterParam userRegisterParam) {
        UserEntity userEntity = new UserEntity();

        initUserEntity(userEntity);

        userRepository.insert(userEntity);

        return Response.success(userEntity);
    }

    private void initUserEntity(UserEntity userEntity) {
        Long now = System.currentTimeMillis();
        userEntity.setUpdateTime(now);
        userEntity.setCreateTime(now);
        userEntity.setStatus(UserStatus.NORMAL.getStatus());
    }
}
