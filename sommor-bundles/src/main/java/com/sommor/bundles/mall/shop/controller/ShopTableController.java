package com.sommor.bundles.mall.shop.controller;

import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.mall.shop.model.ShopQueryParam;
import com.sommor.bundles.mall.shop.model.ShopTable;
import com.sommor.core.component.table.TableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/15
 */
@RestController
@RequestMapping(value = "/api/shop")
public class ShopTableController extends TableController<
        ShopEntity,
        ShopTable,
        ShopQueryParam> {

}
