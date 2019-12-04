package com.sommor.usercenter.entity;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Table("authentications")
public class AuthenticationEntity extends BaseEntity {

    @Getter @Setter
    @Column
    private Integer userId;

    @Getter @Setter
    @Column
    private Integer authTime;

    @Getter @Setter
    @Column
    private Integer expireTime;

    @Getter @Setter
    @Column
    private String sessionKey;
}
