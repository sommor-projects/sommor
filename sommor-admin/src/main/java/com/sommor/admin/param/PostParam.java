package com.sommor.admin.param;

import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.query.config.Conditional;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class PostParam extends Query {

    @Conditional
    @Getter
    @Setter
    private Long id;

    @Conditional
    @Getter
    @Setter
    private Long parentId;

}
