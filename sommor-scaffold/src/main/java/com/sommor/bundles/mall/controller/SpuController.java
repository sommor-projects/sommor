package com.sommor.bundles.mall.controller;

import com.sommor.bundles.mall.entity.SpuEntity;
import com.sommor.bundles.mall.view.SpuFormRenderParam;
import com.sommor.bundles.mall.view.SpuQueryParam;
import com.sommor.bundles.mall.service.SpuService;
import com.sommor.bundles.mall.view.SpuForm;
import com.sommor.bundles.mall.view.SpuTable;
import com.sommor.bundles.user.config.Authority;
import com.sommor.core.curd.CurdController;
import com.sommor.scaffold.param.EntityDetailParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/22
 */
@RestController
@RequestMapping("/api/spu")
@Authority(roles = {"admin"})
public class SpuController extends CurdController<
        SpuEntity,
        SpuForm,
        SpuFormRenderParam,
        SpuEntity,
        EntityDetailParam,
        SpuTable,
        SpuQueryParam> {

    @Resource
    private SpuService spuService;
}
