package com.sommor.bundles.mall.order.model;

import com.sommor.mybatis.entity.config.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@Getter
@Setter
public class Buyer {

    private Long userId;

    private String nickName;

}
