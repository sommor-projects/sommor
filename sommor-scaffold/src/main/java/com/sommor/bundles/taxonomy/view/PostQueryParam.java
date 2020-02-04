package com.sommor.bundles.taxonomy.view;

import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.select.OrderType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Getter @Setter
public class PostQueryParam extends SubjectTaxonomyQueryParam {

    public PostQueryParam() {
        super("post");
    }

    @Override
    public void onQuery(Query query) {
        super.onQuery(query);

        query.orderly("updateTime", OrderType.DESC);
    }
}
