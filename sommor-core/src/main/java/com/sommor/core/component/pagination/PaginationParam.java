package com.sommor.core.component.pagination;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@PaginationParamField
public class PaginationParam {

    @Getter
    @Setter
    private Integer pageNo;

    @Getter @Setter
    private Integer pageSize;
}
