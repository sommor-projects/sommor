package com.sommor.view;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/22
 */
public class PageView<V> extends View<V> {

    @Getter
    @Setter
    private String title;

    @Setter
    private BreadcrumbView breadcrumb;

    public PageView() {
        super("page");
    }

    public List<RouteLinkView> getBreadcrumbs() {
        return null == breadcrumb ? Collections.emptyList() : breadcrumb.getRouteLinks();
    }

    public void setContent(V content) {
        this.value(content);
    }

    public V getContent() {
        return this.value();
    }
}
