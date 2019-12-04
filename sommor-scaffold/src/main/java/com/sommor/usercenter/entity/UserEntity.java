package com.sommor.usercenter.entity;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.usercenter.auth.Encryption;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/1/13
 */
@Table("users")
public class UserEntity extends BaseEntity {

    public static final Integer GENDER_MALE = 0;

    public static final Integer GENDER_FEMALE = 1;

    @Getter @Setter
    @Column
    private String userName;

    @Getter @Setter
    @Column
    private String mobilePhone;

    @Getter @Setter
    @Column
    private String email;

    @Getter @Setter
    @Column
    private String password;

    @Getter @Setter
    @Column
    private String nickName;

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
    private Integer createTime;

    @Getter @Setter
    @Column
    private Integer updateTime;

    @Getter @Setter
    @Column
    private Integer status;

    public static String encryptPassword(String password, int createTime) {
        return Encryption.md5(password + createTime);
    }
}
