package com.sommor.mybatis.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class EntityQueryParam {

    @Getter @Setter
    private Integer pageNo;

    @Getter @Setter
    private Integer pageSize;

    @Getter @Setter
    private String orderBy;

    @Getter @Setter
    private String orderType;

    public void initQuery(Query query) {
    }

}
