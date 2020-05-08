package com.sommor.bundles.user.entity;

import com.sommor.core.component.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.core.utils.Encryption;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/1/13
 */
@Table(value = "users", entityName = UserEntity.NAME, autoIncrement = false)
public class UserEntity extends ConfigurableEntity<Long> {
    public static final String NAME = "user";

    public static final Integer GENDER_MALE = 0;

    public static final Integer GENDER_FEMALE = 1;

    public static final Integer STATUS_NORMAL = 1;

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
    private Integer status;

    public static String encryptPassword(String password, long createTime) {
        return Encryption.md5(password + createTime);
    }
}
