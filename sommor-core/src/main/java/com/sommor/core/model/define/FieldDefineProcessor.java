package com.sommor.core.model.define;

import com.sommor.extensibility.config.Extension;
import com.sommor.core.model.FieldDefinition;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
@Extension(name = "模型字段定义处理器")
public interface FieldDefineProcessor<T> {

    void processOnFieldDefine(T t, FieldDefinition fieldDefinition);

}
