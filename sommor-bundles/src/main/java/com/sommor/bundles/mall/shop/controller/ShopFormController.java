package com.sommor.bundles.mall.shop.controller;

import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.mall.shop.model.ShopForm;
import com.sommor.bundles.mall.shop.service.ShopFormService;
import com.sommor.bundles.taxonomy.model.EntityTaxonomyFormParam;
import com.sommor.core.component.form.FormController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/15
 */
@RestController
@RequestMapping(value = "/api/shop")
public class ShopFormController extends FormController<
        ShopEntity,
        ShopForm,
        EntityTaxonomyFormParam> {

    public ShopFormController(ShopFormService formService) {
        super(formService);
    }
}
