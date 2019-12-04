package com.sommor.view;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/23
 */
@Getter
@Setter
public class OptionTree {

    private String key;

    private String value;

    private String title;

    private List<OptionTree> children;

    public OptionTree() {
    }

    public OptionTree(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public void addChild(OptionTree optionTree) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.add(optionTree);
    }
}
