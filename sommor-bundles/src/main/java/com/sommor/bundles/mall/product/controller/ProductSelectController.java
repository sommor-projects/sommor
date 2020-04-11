package com.sommor.bundles.mall.product.controller;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.model.ProductQueryParam;
import com.sommor.core.component.entity.select.EntitySelectController;
import com.sommor.extensibility.config.Implement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
@Implement
@RestController
@RequestMapping(value = "/api/product")
public class ProductSelectController extends EntitySelectController<ProductEntity, ProductQueryParam> {
}
