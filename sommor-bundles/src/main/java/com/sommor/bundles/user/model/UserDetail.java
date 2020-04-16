package com.sommor.bundles.user.model;

import com.sommor.core.model.EntityDetail;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/25
 */
@Getter
@Setter
public class UserDetail extends EntityDetail {

    private String userName;

    private String mobilePhone;

    private String email;

    private String nickName;

    private Integer gender;

    private String avatar;

    private Integer status;

}
