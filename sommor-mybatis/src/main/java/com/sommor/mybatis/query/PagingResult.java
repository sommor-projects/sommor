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
    private List<Entity> entities;

    @Getter
    @Setter
    private Integer totalSize;

    @Getter
    @Setter
    private Integer currentPage;

    @Getter
    @Setter
    private Integer totalPage;

    @Getter
    @Setter
    private Integer pageSize;

    @Getter
    @Setter
    private Boolean isEnded;
}
