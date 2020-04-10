package com.sommor.bundles.menu.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
public class MenuItem implements Comparable<MenuItem> {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String url;

    @Getter @Setter
    private String icon;

    @Getter @Setter
    private int priority;

    @Getter @Setter
    private boolean active;

    @Override
    public int compareTo(MenuItem o) {
        return o.priority - this.priority;
    }
}
