package com.sommor.bundle.mall.product.component.shop;

import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.mall.shop.model.ShopInfoVO;
import com.sommor.bundle.mall.shop.repository.ShopRepository;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillProcessor;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
@Implement
public class ShopDisplayFieldProcessor implements FieldFillProcessor<ShopDisplayFieldConfig> {

    @Resource
    private ShopRepository shopRepository;

    @Override
    public Object processOnFieldFill(ShopDisplayFieldConfig config, FieldFillContext ctx) {
        Integer shopId = config.getShopId();
        if (null != shopId && shopId > 0) {
            ShopEntity shopEntity = shopRepository.findById(shopId);
            ShopInfoVO shopInfoVO = new ShopInfoVO();
            shopInfoVO.setShopId(String.valueOf(shopId));
            shopInfoVO.setTitle(shopEntity.getTitle());
            shopInfoVO.setSubTitle(shopEntity.getSubTitle());

            return shopInfoVO;
        }

        return null;
    }
}
