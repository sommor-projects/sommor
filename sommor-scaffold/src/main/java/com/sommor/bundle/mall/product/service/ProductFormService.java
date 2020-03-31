package com.sommor.bundle.mall.product.service;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.mall.product.model.ProductForm;
import com.sommor.bundle.mall.product.model.ProductFormParam;
import com.sommor.component.form.FormService;
import org.springframework.stereotype.Service;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/1
 */
@Service
public class ProductFormService extends FormService<
        ProductEntity,
        ProductForm,
        ProductFormParam> {
}
