package com.sommor.view;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/22
 */
@Getter
@Setter
public class BreadcrumbView extends View<List<RouteLinkView>> {

    public BreadcrumbView() {
        super("breadcrumb");
    }

    public void addRouteLink(RouteLinkView routeLinkView) {
        if (null == this.value()) {
            this.value(new ArrayList<>());
        }
        this.value().add(routeLinkView);
    }

    public void setRouteLinks(List<RouteLinkView> routeLinks) {
        this.value(routeLinks);
    }

    public List<RouteLinkView> getRouteLinks() {
        return value();
    }
}
