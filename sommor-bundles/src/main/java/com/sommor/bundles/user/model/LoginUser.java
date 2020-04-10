package com.sommor.bundles.user.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Getter
@Setter
public class LoginUser {

    private String token;

    private Integer expireTime;

    private UserProfile userProfile;

    private Set<String> roles;
}
