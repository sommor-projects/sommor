package com.sommor.bundle.mall.product.controller;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.mall.product.model.*;
import com.sommor.component.table.TableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
