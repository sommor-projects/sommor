package com.sommor.scaffold.view.field.action;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class SearchAction implements FormAction {
    @Override
    public String action() {
        return "search";
    }

    @Override
    public String actionTitle() {
        return "搜索";
    }
}
