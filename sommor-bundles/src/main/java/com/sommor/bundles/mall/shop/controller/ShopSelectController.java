package com.sommor.bundles.mall.shop.controller;

import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.mall.shop.model.ShopQueryParam;
import com.sommor.core.component.entity.select.EntitySelectController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
@RestController
@RequestMapping(value = "/api/shop")
public class ShopSelectController extends EntitySelectController<ShopEntity, ShopQueryParam> {

}
