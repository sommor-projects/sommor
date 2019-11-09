package com.sommor.usercenter.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
public class UserRegisterParam {

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String telephone;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String nickname;

    @Getter @Setter
    private Integer gender;

    @Getter @Setter
    private String avatar;

    @Getter @Setter
    private String birthday;

}
