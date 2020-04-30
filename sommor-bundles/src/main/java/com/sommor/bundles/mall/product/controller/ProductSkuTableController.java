package com.sommor.bundles.mall.product.controller;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.model.ProductQueryParam;
import com.sommor.bundles.mall.product.model.ProductSkuTable;
import com.sommor.bundles.mall.product.model.ProductTable;
import com.sommor.core.component.table.TableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@RestController
@RequestMapping(value = "/api/product-sku")
public class ProductSkuTableController extends TableController<
        ProductEntity,
        ProductSkuTable,
        ProductQueryParam> {
}
