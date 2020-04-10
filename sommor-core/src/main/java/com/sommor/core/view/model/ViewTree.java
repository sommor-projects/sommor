package com.sommor.core.view.model;

import com.sommor.core.view.View;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/27
 */
public class ViewTree extends View {

    @Getter
    private List<View> views = new ArrayList<>();

    public ViewTree() {
        super("view-tree");
    }

    public void addView(View view) {
        views.add(view);
    }
}
