package com.sommor.bundles.mall.controller;

import com.sommor.bundles.mall.entity.SkuEntity;
import com.sommor.bundles.mall.view.SkuForm;
import com.sommor.bundles.mall.view.SkuFormRenderParam;
import com.sommor.bundles.mall.view.SkuQueryParam;
import com.sommor.core.curd.CurdController;
import com.sommor.scaffold.param.EntityDetailParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@RestController
@RequestMapping(value = "/api/sku")
public class SkuController extends CurdController<
        SkuEntity,
        SkuForm,
        SkuFormRenderParam,
        SkuEntity,
        EntityDetailParam,
        SkuEntity,
        SkuQueryParam> {

}
