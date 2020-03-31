package com.sommor.component.orderby;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@OrderByParamField
public class OrderByParam {

    @Getter
    @Setter
    private String field;

    @Getter
    @Setter
    private String type;
}
