package com.sommor.bundle.mall.product.service;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.mall.product.entity.SkuEntity;
import com.sommor.bundle.mall.product.model.SkuForm;
import com.sommor.bundle.mall.product.model.SkuFormParam;
import com.sommor.component.form.FormService;
import com.sommor.model.Model;
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
}
