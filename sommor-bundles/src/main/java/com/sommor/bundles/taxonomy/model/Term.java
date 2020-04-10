package com.sommor.bundles.taxonomy.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */
@Getter
@Setter
public class Term {

    private String title;

    private String subTitle;

    @Override
    public String toString() {
        return title + "(" + subTitle + ")";
    }
}
