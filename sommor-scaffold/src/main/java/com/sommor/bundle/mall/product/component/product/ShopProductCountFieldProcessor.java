package com.sommor.bundle.mall.product.component.product;

import com.sommor.bundle.mall.product.repository.ProductRepository;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillProcessor;
import com.sommor.mybatis.query.Query;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/7
 */
@Implement
public class ShopProductCountFieldProcessor implements FieldFillProcessor<ShopProductCountFieldConfig> {

    @Resource
    private ProductRepository productRepository;

    @Override
    public Object processOnFieldFill(ShopProductCountFieldConfig config, FieldFillContext ctx) {
        Integer shopId = config.getShopId();

        Query query = new Query()
                .where("shopId", shopId);

        int count = productRepository.count(query);

        return count;
    }
}
