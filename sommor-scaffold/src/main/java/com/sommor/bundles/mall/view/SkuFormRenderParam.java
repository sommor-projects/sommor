package com.sommor.bundles.mall.view;

import com.sommor.scaffold.param.EntityFormRenderParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class SkuFormRenderParam extends EntityFormRenderParam {

    @Getter
    @Setter
    private Integer shopId;

    @Getter
    @Setter
    private Integer spuId;

    @Getter
    @Setter
    private Integer distributedSkuId;
}
