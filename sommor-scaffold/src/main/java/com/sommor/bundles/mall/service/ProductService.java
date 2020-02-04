package com.sommor.bundles.mall.service;

import com.sommor.bundles.mall.entity.ProductEntity;
import com.sommor.bundles.mall.view.*;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.core.curd.CurdService;
import org.springframework.stereotype.Service;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Service
public class ProductService extends CurdService<
        ProductEntity,
        ProductForm,
        ProductFormRenderParam,
        ProductDetail,
        EntityDetailParam,
        ProductTable,
        ProductQueryParam> {

    @Override
    protected void onDelete(ProductEntity entity) {
        super.onDelete(entity);
    }
}
