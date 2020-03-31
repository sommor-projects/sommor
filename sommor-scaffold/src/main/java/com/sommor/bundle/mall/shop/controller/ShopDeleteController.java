package com.sommor.bundle.mall.shop.controller;

import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.curd.delete.EntityDeleteController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
@RestController
@RequestMapping(value = "/api/shop")
public class ShopDeleteController extends EntityDeleteController<ShopEntity> {
}
