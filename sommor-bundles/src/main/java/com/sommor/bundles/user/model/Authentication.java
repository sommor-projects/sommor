package com.sommor.bundles.user.model;

import com.sommor.bundles.user.jwt.Jwtoken;
import lombok.Data;

/**
 * @author wuyu
 * @since 2019/2/5
 */
@Data
public class Authentication {

    private Long id;

    private Long userId;

    private Jwtoken jwtoken;

    private Integer expireTime;

    private String sessionKey;
}
