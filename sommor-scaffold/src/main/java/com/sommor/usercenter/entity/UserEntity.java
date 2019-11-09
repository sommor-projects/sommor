package com.sommor.usercenter.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/1/13
 */
@Table("users")
public class UserEntity {

    public static final Integer GENDER_MALE = 0;

    public static final Integer GENDER_FEMALE = 1;

    @Getter @Setter
    @Column
    private Integer id;

    @Getter @Setter
    @Column
    private String username;

    @Getter @Setter
    @Column
    private String telephone;

    @Getter @Setter
    @Column
    private String email;

    @Getter @Setter
    @Column
    private String password;

    @Getter @Setter
    @Column
    private String nickname;

    /**
     * @see UserEntity#GENDER_MALE
     * @see UserEntity#GENDER_FEMALE
     */
    @Getter @Setter
    @Column
    private Integer gender;

    @Getter @Setter
    @Column
    private String avatar;

    @Getter @Setter
    @Column
    private String birthday;

    @Getter @Setter
    @Column
    private Long createTime;

    @Getter @Setter
    @Column
    private Long updateTime;

    @Getter @Setter
    @Column
    private Integer status;
}
