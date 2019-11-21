package com.sommor.usercenter.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Table("authentications")
public class AuthenticationEntity {

    @Getter @Setter
    @Column
    private Integer id;

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
