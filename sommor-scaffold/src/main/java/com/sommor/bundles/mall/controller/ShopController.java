package com.sommor.bundles.mall.controller;

import com.sommor.bundles.mall.view.ShopQueryParam;
import com.sommor.bundles.mall.view.ShopDetail;
import com.sommor.bundles.mall.view.ShopTable;
import com.sommor.bundles.taxonomy.view.SubjectFormRenderParam;
import com.sommor.scaffold.controller.CurdController;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.bundles.mall.entity.ShopEntity;
import com.sommor.bundles.mall.view.ShopForm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/15
 */
@RestController
@RequestMapping(value = "/api/shop")
public class ShopController extends CurdController<
        ShopEntity,
        ShopForm,
        SubjectFormRenderParam,
        ShopDetail,
        EntityDetailParam,
        ShopTable,
        ShopQueryParam> {

}
