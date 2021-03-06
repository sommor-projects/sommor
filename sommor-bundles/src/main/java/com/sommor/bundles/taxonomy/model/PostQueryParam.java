package com.sommor.bundles.taxonomy.model;

import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.select.OrderType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Getter @Setter
public class PostQueryParam extends EntityTaxonomyQueryParam {

    public PostQueryParam() {
        super("post");
    }

    @Override
    public void onModelQuery(Query query) {
        super.onModelQuery(query);

        query.orderly("updateTime", OrderType.DESC);
    }
}
