package com.sommor.bundles.mall.controller;

import com.sommor.bundles.mall.entity.ProductEntity;
import com.sommor.bundles.mall.view.*;
import com.sommor.core.curd.CurdController;
import com.sommor.scaffold.param.EntityDetailParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@RestController
@RequestMapping(value = "/api/product")
public class ProductController extends CurdController<
        ProductEntity,
        ProductForm,
        ProductFormRenderParam,
        ProductDetail,
        EntityDetailParam,
        ProductTable,
        ProductQueryParam> {
}
