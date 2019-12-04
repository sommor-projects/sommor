package com.sommor.extensibility;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class ExtensionDefinition {

    private Class clazz;

    private String name;

    private boolean annotated;

    public ExtensionDefinition(Class clazz, String name, boolean annotated) {
        this.clazz = clazz;
        this.name = name;
        this.annotated = annotated;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public boolean annotated() {
        return annotated;
    }
}
