package com.sommor.taxonomy.param;

import com.sommor.mybatis.query.EntityQueryParam;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.query.config.Conditional;
import com.sommor.mybatis.query.config.QueryConfig;
import com.sommor.mybatis.sql.select.OrderType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Getter @Setter
@QueryConfig(enablePageable = true, enableOrderly = true)
public class PostQueryParam extends EntityQueryParam {

    private String type;

    @Conditional
    private Integer typeId;

    @Override
    public void initQuery(Query query) {
        query.orderly("updateTime", OrderType.DESC);
    }
}
