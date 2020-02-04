package com.sommor.core.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
public class ListView extends FieldView {

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String subTitle;

    @Getter
    @Setter
    List<ListItemView> items;

    public ListView() {
        super("list");
    }
}
