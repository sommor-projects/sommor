package com.sommor.bundles.mall.product.controller;

import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.model.SkuQueryParam;
import com.sommor.bundles.mall.product.model.SkuTable;
import com.sommor.core.component.table.TableController;
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
