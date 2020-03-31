package com.sommor.component.form.action;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/26
 */
abstract public class AbstractAction implements FormAction {

    private Class actionClass;

    public AbstractAction(Class actionClass) {
        this.actionClass = actionClass;
    }

    @Override
    public Class actionClass() {
        return this.actionClass;
    }
}
