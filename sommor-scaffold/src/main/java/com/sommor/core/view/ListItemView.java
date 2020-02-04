package com.sommor.core.view;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
public class ListItemView extends View {

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String subTitle;

    @Getter
    @Setter
    private String icon;

    @Getter
    @Setter
    private String navigation;

    public ListItemView() {
        super("list-item");
    }
}
