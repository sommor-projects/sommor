package com.sommor.scaffold.view.field.action;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/16
 */
public class AddAction implements FormAction {

    @Override
    public String action() {
        return "add";
    }

    @Override
    public String actionTitle() {
        return "添加";
    }

}
