package com.sommor.component.form.action;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/16
 */
public class AddAction extends AbstractAction {

    public AddAction() {
        super(Add.class);
    }

    @Override
    public String action() {
        return "add";
    }

    @Override
    public String actionTitle() {
        return "添加";
    }

}
