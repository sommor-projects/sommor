package com.sommor.wechat.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuyu
 * @since 2019/2/5
 */
@Table("wechat_users")
public class WechatUserEntity {

    @Getter
    @Setter
    @Column
    private Integer id;

    @Getter
    @Setter
    @Column
    private Integer userId;

    @Getter
    @Setter
    @Column
    private String openid;

    @Getter
    @Setter
    @Column
    private String unionid;

    @Getter
    @Setter
    @Column
    private String nickname;

    @Getter
    @Setter
    @Column
    private String avatar;
}
