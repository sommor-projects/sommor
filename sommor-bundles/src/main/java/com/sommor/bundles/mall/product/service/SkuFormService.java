package com.sommor.bundles.mall.product.service;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.model.SkuForm;
import com.sommor.bundles.mall.product.model.SkuFormParam;
import com.sommor.core.component.form.FormService;
import com.sommor.core.model.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/1
 */
@Service
public class SkuFormService extends FormService<
        SkuEntity,
        SkuForm,
        SkuFormParam> {

    @Override
    protected void onFormSaving(Model model, SkuEntity entity, SkuEntity originalEntity) {
        super.onFormSaving(model, entity, originalEntity);

        ProductEntity productEntity = model.getExt(ProductEntity.class);
        entity.setProductType(productEntity.getProductType());

        SkuForm skuForm = model.getTarget();
        if (StringUtils.isBlank(skuForm.getTitle())) {
            skuForm.getTaxonomy();
        }
    }

    @Override
    protected void onEntityFormRender(SkuForm skuForm, SkuFormParam skuFormParam, SkuEntity entity) {
        super.onEntityFormRender(skuForm, skuFormParam, entity);


    }
}
