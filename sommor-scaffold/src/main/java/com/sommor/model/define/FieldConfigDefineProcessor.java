package com.sommor.model.define;

import com.sommor.extensibility.config.Extension;
import com.sommor.model.FieldDefinition;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/16
 */
@Extension(name = "字段定义处理器")
public interface FieldConfigDefineProcessor<FC> {

    void processOnFieldConfigDefine(FieldDefinition fieldDefinition);

}
