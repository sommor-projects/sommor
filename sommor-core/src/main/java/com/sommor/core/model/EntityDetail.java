package com.sommor.core.model;

import com.sommor.core.model.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/25
 */
public class EntityDetail {

    @Getter
    @Setter
    private String id;

    public void fill(Object source) {
        Model.of(this).fill(Model.of(source));
    }
}
