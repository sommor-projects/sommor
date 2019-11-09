package com.sommor.extensibility;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class ExtensionDefinition {

    private Class clazz;

    private String name;

    private boolean hasAnnotatedTypes;

    public ExtensionDefinition(Class clazz, String name, boolean hasAnnotatedTypes) {
        this.clazz = clazz;
        this.name = name;
        this.hasAnnotatedTypes = hasAnnotatedTypes;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public boolean hasAnnotatedTypes() {
        return hasAnnotatedTypes;
    }
}
