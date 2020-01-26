package com.sommor.scaffold.view.field.action;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/16
 */
public class EditionAction implements FormAction {

    @Override
    public String action() {
        return "edit";
    }

    @Override
    public String actionTitle() {
        return "编辑";
    }
}
