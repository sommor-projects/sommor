package com.sommor.usercenter.entity;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.usercenter.validator.UserIdExist;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@Table("user_roles")
@Setter
@Getter
public class UserRoleEntity extends BaseEntity {

    @Column
    @UserIdExist
    private Integer userId;

    @Column
    @NotBlank
    private String role;

}
