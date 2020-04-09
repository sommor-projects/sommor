package com.sommor.bundle.mall.shop.controller;

import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.mall.shop.model.ShopDetail;
import com.sommor.component.detail.DetailController;
import com.sommor.scaffold.param.EntityDetailParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/5
 */
@RestController
@RequestMapping(value = "/api/shop")
public class ShopDetailController extends DetailController<ShopEntity, ShopDetail, EntityDetailParam> {
}
