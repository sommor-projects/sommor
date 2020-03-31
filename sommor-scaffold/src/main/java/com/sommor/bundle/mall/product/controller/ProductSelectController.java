package com.sommor.bundle.mall.product.controller;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.mall.product.model.ProductQueryParam;
import com.sommor.component.entity.select.EntitySelectController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
@RestController
@RequestMapping(value = "/api/product")
public class ProductSelectController extends EntitySelectController<ProductEntity, ProductQueryParam> {
}
