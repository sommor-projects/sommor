package com.sommor.bundles.taxonomy.view;

import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.query.config.Conditional;
import com.sommor.mybatis.sql.select.OrderType;
import com.sommor.scaffold.param.EntitySearchParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Getter @Setter
public class PostQueryParam extends EntitySearchParam {

    private String type;

    @Conditional
    private Integer typeId;

    @Override
    public void onQuery(Query query) {
        query.orderly("updateTime", OrderType.DESC);
    }
}
