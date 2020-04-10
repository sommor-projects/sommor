package com.sommor.bundles.mall.product.controller;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.model.*;
import com.sommor.core.component.table.TableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@RestController
@RequestMapping(value = "/api/product")
public class ProductTableController extends TableController<
        ProductEntity,
        ProductTable,
        ProductQueryParam> {
}
