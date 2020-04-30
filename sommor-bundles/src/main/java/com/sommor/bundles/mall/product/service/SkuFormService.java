package com.sommor.bundles.mall.product.service;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.model.SkuForm;
import com.sommor.bundles.mall.product.model.SkuFormParam;
import com.sommor.core.component.form.EntityFormService;
import com.sommor.core.model.Model;
import org.springframework.stereotype.Service;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/1
 */
@Service
public class SkuFormService extends EntityFormService<
        SkuEntity,
        SkuForm,
        SkuFormParam> {

    @Override
    protected void onEntityFormRender(SkuForm skuForm, SkuFormParam skuFormParam, SkuEntity entity) {
        super.onEntityFormRender(skuForm, skuFormParam, entity);


    }
}
