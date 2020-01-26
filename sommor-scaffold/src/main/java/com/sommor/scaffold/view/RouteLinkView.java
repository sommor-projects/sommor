package com.sommor.scaffold.view;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/22
 */
@Getter
@Setter
public class RouteLinkView extends View {

    private String title;

    private String name;

    private Map<String, Object> params = new HashMap<>();

    public RouteLinkView() {
        super("route-link");
    }

    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

}
