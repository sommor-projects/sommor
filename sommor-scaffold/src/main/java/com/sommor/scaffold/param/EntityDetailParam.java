package com.sommor.scaffold.param;

import com.sommor.mybatis.query.Query;
import com.sommor.curd.query.OnModelQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class EntityDetailParam implements OnModelQuery {

    @Getter @Setter
    private Integer id;

    @Override
    public void onModelQuery(Query query) {
        if (null != id && id > 0) {
            query.where("id", id);
        }
    }
}
