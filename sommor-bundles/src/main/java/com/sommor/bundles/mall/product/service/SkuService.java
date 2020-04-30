package com.sommor.bundles.mall.product.service;

import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.repository.SkuRepository;
import com.sommor.core.curd.CurdService;
import com.sommor.core.generator.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Service
public class SkuService extends CurdService<SkuEntity, Long> {

    @Resource
    private SkuRepository skuRepository;

    @Resource
    private IdGenerator productIdGenerator;

    @Override
    protected void onSaving(SkuEntity entity, SkuEntity originalEntity) {
        super.onSaving(entity, originalEntity);

        if (null == originalEntity) {
            if (null == entity.getId()) {
                entity.setId(productIdGenerator.generateId());
            }
        }
    }

}
