package com.sommor.core.scaffold.param;

import com.sommor.core.utils.Converter;
import com.sommor.mybatis.query.Query;
import com.sommor.core.curd.query.OnModelQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class EntityDetailParam implements OnModelQuery {

    @Getter @Setter
    private String id;

    @Override
    public void onModelQuery(Query query) {
        Long id = Converter.parseLong(this.id);
        if (null != id && id > 0) {
            query.where("id", id);
        }
    }
}
