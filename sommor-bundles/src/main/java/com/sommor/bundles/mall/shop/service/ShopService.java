package com.sommor.bundles.mall.shop.service;

import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.core.curd.CurdService;
import com.sommor.core.generator.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/28
 */
@Service
public class ShopService extends CurdService<ShopEntity> {

    @Resource
    private IdGenerator shopIdGenerator;

    @Override
    protected void onSaving(ShopEntity entity, ShopEntity originalEntity) {
        super.onSaving(entity, originalEntity);
        if (null == originalEntity) {
            entity.setId(shopIdGenerator.generateId());
        }
    }
}
