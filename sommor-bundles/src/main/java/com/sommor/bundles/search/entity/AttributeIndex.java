package com.sommor.bundles.search.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@Getter
@Setter
public class AttributeIndex {

    private String name;

    private List<String> values;

    public void addValue(String value) {
        if (null == values) {
            values = new ArrayList<>();
        }

        if (! values.contains(value)) {
            values.add(value);
        }
    }
}
