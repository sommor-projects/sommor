package com.sommor.view;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Getter
public class FieldView extends View {

    private FieldConstraints constraints = new FieldConstraints();

    public FieldView(String type) {
        super(type);
    }


}
