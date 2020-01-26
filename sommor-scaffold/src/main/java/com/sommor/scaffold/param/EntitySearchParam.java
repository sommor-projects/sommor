package com.sommor.scaffold.param;

import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.view.field.OnQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/25
 */
public class EntitySearchParam implements OnQuery {

    @Getter @Setter
    private Integer pageNo;

    @Getter @Setter
    private Integer pageSize;

    @Getter @Setter
    private String orderBy;

    @Getter @Setter
    private String orderType;

    @Override
    public void onQuery(Query query) {
        if (null != pageNo) {
            query.pageable(pageNo, pageSize);
        }

        if (null != orderBy) {
            query.orderly(orderBy, orderType);
        }
    }
}
