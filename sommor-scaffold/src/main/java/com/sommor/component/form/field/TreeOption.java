package com.sommor.component.form.field;

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
public class TreeOption extends Option {

    private List<TreeOption> children;

    public TreeOption(String label, Object value) {
        super(label, value);
    }

    public void addChild(TreeOption treeOption) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.add(treeOption);
    }
}
