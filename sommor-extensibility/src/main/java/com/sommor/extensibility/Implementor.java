package com.sommor.extensibility;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/23
 */
public class Implementor<Ext> implements Comparable<Implementor> {

    private Plugin plugin;

    private Ext target;

    private int priority;

    public Implementor(Plugin plugin, Ext target, int priority) {
        this.plugin = plugin;
        this.target = target;
        this.priority = priority;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Ext getTarget() {
        return target;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Implementor o) {
        return o.getPriority() - this.getPriority();
    }
}
