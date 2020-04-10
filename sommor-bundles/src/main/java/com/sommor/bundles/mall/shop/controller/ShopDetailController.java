package com.sommor.bundles.mall.shop.controller;

import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.mall.shop.model.ShopDetail;
import com.sommor.core.component.detail.DetailController;
import com.sommor.core.scaffold.param.EntityDetailParam;
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
