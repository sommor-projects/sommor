package com.sommor.bundle.user.jwt;

import lombok.Getter;

/**
 * @author wuyu
 * @since 2019/2/1
 */
public class JwtSession {

    @Getter
    private int id;

    @Getter
    private long expiredTime;

    public JwtSession(int id, long expiredTime) {
        this.id = id;
        this.expiredTime = expiredTime;
    }
}
