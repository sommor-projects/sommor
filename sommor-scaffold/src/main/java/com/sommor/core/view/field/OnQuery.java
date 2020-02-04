package com.sommor.core.view.field;

import com.sommor.mybatis.query.Query;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public interface OnQuery {

    void onQuery(Query query);

}
