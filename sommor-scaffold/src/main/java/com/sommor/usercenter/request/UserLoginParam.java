package com.sommor.usercenter.request;

import javax.validation.constraints.NotBlank;

/**
 * @author wuyu
 * @since 2019/2/3
 */
public class UserLoginParam {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
