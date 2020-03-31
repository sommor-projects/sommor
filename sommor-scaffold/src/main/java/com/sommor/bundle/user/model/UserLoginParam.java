package com.sommor.bundle.user.model;

import javax.validation.constraints.NotBlank;

/**
 * @author wuyu
 * @since 2019/2/3
 */
public class UserLoginParam {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
