package com.sommor.mybatis.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/26
 */
public class PagingResult<Entity> {

    @Getter
    @Setter
    private List<Entity> data;

    @Getter
    @Setter
    private Integer totalCount;

    @Getter
    @Setter
    private Integer pageNo;

    @Getter
    @Setter
    private Integer totalPage;

    @Getter
    @Setter
    private Integer pageSize;

    @Getter
    @Setter
    private Boolean isEnded;

    public static <Entity> PagingResult<Entity> of(List<Entity> data) {
        PagingResult<Entity> pagingResult = new PagingResult<>();
        pagingResult.setData(data);
        pagingResult.setTotalCount(data.size());
        pagingResult.setTotalPage(1);
        pagingResult.setPageNo(1);
        pagingResult.setPageSize(data.size());
        pagingResult.setIsEnded(true);

        return pagingResult;
    }
}
