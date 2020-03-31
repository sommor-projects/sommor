package com.sommor.view.extension;

import com.sommor.extensibility.config.Extension;
import com.sommor.model.config.TargetConfig;

import java.lang.annotation.Annotation;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
@Extension(name = "视图组件配置填充处理器")
public interface ViewAnnotationRenderProcessor<A extends Annotation, TC extends TargetConfig> {

    void processOnViewAnnotationRender(A a, TC tc);

}
