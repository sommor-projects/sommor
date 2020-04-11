package com.sommor.bundles.user.jwt;

import lombok.Getter;

/**
 * @author wuyu
 * @since 2019/2/1
 */
public class JwtSession {

    @Getter
    private long id;

    @Getter
    private long expiredTime;

    public JwtSession(long id, long expiredTime) {
        this.id = id;
        this.expiredTime = expiredTime;
    }
}
