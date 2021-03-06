package com.sommor.bundles.wechat.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.bundles.wechat.entity.WechatUserEntity;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
public interface WechatUserRepository extends CurdRepository<WechatUserEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    WechatUserEntity findByOpenid(String openid);

}
