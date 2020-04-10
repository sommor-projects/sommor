package com.sommor.bundles.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
public class UserRegisterParam {

    @Getter @Setter
    @NotBlank
    private String username;

    @Getter @Setter
    private String mobilePhone;

    @Getter @Setter
    private String email;

    @Getter @Setter
    @NotBlank
    private String password;

    @Getter @Setter
    @NotBlank
    private String nickname;

    @Getter @Setter
    private Integer gender;

    @Getter @Setter
    private String avatar;
}
