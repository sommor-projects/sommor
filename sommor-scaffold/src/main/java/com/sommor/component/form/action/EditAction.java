package com.sommor.component.form.action;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/16
 */
public class EditAction extends AbstractAction {
    public EditAction() {
        super(Edit.class);
    }

    @Override
    public String action() {
        return "edit";
    }

    @Override
    public String actionTitle() {
        return "编辑";
    }
}
