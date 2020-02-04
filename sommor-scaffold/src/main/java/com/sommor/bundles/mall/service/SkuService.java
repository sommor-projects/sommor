package com.sommor.bundles.mall.service;

import com.sommor.bundles.mall.entity.SkuEntity;
import com.sommor.bundles.mall.view.SkuForm;
import com.sommor.bundles.mall.view.SkuFormRenderParam;
import com.sommor.bundles.mall.view.SkuQueryParam;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.core.curd.CurdService;
import org.springframework.stereotype.Service;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Service
public class SkuService extends CurdService<
        SkuEntity,
        SkuForm,
        SkuFormRenderParam,
        SkuEntity,
        EntityDetailParam,
        SkuEntity,
        SkuQueryParam> {
}
