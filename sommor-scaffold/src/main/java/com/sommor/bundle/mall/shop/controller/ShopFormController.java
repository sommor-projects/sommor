package com.sommor.bundle.mall.shop.controller;

import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.mall.shop.model.ShopForm;
import com.sommor.bundle.mall.shop.service.ShopFormService;
import com.sommor.bundle.taxonomy.model.TaxonomyRelationFormParam;
import com.sommor.component.form.FormController;
import com.sommor.component.form.FormService;
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
        TaxonomyRelationFormParam> {

    public ShopFormController(ShopFormService formService) {
        super(formService);
    }
}
