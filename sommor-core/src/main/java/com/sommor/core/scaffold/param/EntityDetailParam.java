package com.sommor.core.scaffold.param;

import com.sommor.core.utils.Converter;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.query.Query;
import com.sommor.core.curd.query.OnModelQuery;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class EntityDetailParam implements OnModelQuery {

    @Getter @Setter
    private Object id;

    @Override
    public void onModelQuery(Query query) {
        if (! BaseEntity.isIdEmpty(id)) {
            query.where("id", id);
        }
    }
}
