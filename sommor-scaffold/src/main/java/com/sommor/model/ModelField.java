package com.sommor.model;

import com.sommor.core.context.IExtensible;
import com.sommor.model.config.TargetConfig;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public interface ModelField extends IExtensible {

    String getName();

    void setValue(Object value);

    Object getValue();

    Model getModel();

    Model getSubModel();

    Class getType();

    <T extends TargetConfig> T getFieldConfig();
}
