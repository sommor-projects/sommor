package com.sommor.bundles.outline.model;

import com.sommor.core.component.conditional.Conditional;
import com.sommor.core.scaffold.param.EntityQueryParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
public class OutlineAccessKeyQueryParam extends EntityQueryParam {

    @Getter
    @Setter
    @Conditional
    private String outlineServerId;
}
