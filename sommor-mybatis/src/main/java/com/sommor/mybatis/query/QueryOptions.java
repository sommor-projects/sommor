package com.sommor.mybatis.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/25
 */
public class QueryOptions {

    @Getter
    @Setter
    private boolean pageableEnabled;

    @Getter
    @Setter
    private boolean orderlyEnabled;
}
