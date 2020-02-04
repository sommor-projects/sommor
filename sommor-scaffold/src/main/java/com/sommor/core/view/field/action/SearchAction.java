package com.sommor.core.view.field.action;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class SearchAction extends AbstractAction {

    public SearchAction() {
        super(Search.class);
    }

    @Override
    public String action() {
        return "search";
    }

    @Override
    public String actionTitle() {
        return "搜索";
    }
}
