package com.sommor.usercenter.model;

import com.sommor.usercenter.entity.UserEntity;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
public class Identity {

    private UserEntity userEntity;

    private String sessionKey;

    public Identity(UserEntity userEntity, String sessionKey) {
        this.userEntity = userEntity;
        this.sessionKey = sessionKey;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public String getSessionKey() {
        return sessionKey;
    }
}
