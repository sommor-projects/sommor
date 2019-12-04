package com.sommor.mybatis.sql.select;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/24
 */
public class Limitation {

    @Getter
    @Setter
    private Integer offset;

    @Getter
    @Setter
    private Integer count;

    public Limitation() {
    }

    public Limitation(Integer offset, Integer count) {
        this.offset = offset;
        this.count = count;
    }
}
