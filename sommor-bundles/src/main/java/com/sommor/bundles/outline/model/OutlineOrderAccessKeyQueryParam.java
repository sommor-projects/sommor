package com.sommor.bundles.outline.model;

import com.sommor.core.component.conditional.Conditional;
import com.sommor.core.scaffold.param.EntityQueryParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/3
 */
@Getter
@Setter
public class OutlineOrderAccessKeyQueryParam extends EntityQueryParam {

    @Conditional
    private Long orderId;

}
