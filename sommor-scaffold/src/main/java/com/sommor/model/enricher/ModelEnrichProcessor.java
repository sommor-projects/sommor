package com.sommor.model.enricher;

import com.sommor.extensibility.config.Extension;
import com.sommor.model.config.TargetConfig;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
@Extension(name = "模型信息丰富处理器")
public interface ModelEnrichProcessor<Config extends TargetConfig> {

    void processOnModelEnrich(Config config, ModelEnrichContext ctx);

}
