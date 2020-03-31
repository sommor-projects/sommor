package com.sommor.model.fill;

import com.sommor.extensibility.config.Extension;
import com.sommor.model.config.TargetConfig;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
@Extension(name = "字段填充处理器")
public interface FieldFillProcessor<Config extends TargetConfig> {

    Object processOnFieldFill(Config config, FieldFillContext ctx);

}
