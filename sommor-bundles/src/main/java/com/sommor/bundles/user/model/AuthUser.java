package com.sommor.bundles.user.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@Getter
@Setter
public class AuthUser {

    private Long authId;

    private String token;

    private Integer expireTime;

    private Long userId;

    private Set<String> roles;

    public boolean hasRole(String role) {
        return null != roles && roles.contains(role);
    }

    public boolean hasRoles(String... roles) {
        for (String role : roles) {
            if (! this.hasRole(role)) {
                return false;
            }
        }
        return true;
    }
}
