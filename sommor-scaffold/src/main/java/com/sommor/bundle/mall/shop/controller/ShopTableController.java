package com.sommor.bundle.mall.shop.controller;

import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.mall.shop.model.ShopQueryParam;
import com.sommor.bundle.mall.shop.model.ShopTable;
import com.sommor.component.table.TableController;
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
