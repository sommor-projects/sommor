package com.sommor.mybatis.sql.field.type;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
public class Array {

    private Collection collection;

    public Array(Collection collection) {
        this.collection = collection;
    }

    public Collection getCollection() {
        return collection == null ? Collections.emptyList() : collection;
    }

    public int size() {
        return collection == null ? 0 : collection.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Object value : this.getCollection()) {
            if (builder.length() > 0) {
                builder.append(",");
            }

            if (value instanceof Number) {
                builder.append(value);
            } else {
                builder.append("'").append(value).append("'");
            }
        }

        return builder.toString();
    }
}
