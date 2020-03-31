package com.sommor.bundle.mall.product.controller;

import com.sommor.bundle.mall.product.entity.SkuEntity;
import com.sommor.bundle.mall.product.model.SkuQueryParam;
import com.sommor.bundle.mall.product.model.SkuTable;
import com.sommor.component.table.TableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@RestController
@RequestMapping(value = "/api/sku")
public class SkuTableController extends TableController<
        SkuEntity,
        SkuTable,
        SkuQueryParam> {

}
