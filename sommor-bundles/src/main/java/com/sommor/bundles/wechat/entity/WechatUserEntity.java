package com.sommor.bundles.wechat.entity;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuyu
 * @since 2019/2/5
 */
@Table("wechat_users")
public class WechatUserEntity extends BaseEntity {

    @Getter
    @Setter
    @Column
    private Long userId;

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
