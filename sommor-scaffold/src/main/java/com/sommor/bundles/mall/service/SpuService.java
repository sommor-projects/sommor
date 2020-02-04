package com.sommor.bundles.mall.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundles.mall.entity.ShopEntity;
import com.sommor.bundles.mall.entity.SpuEntity;
import com.sommor.bundles.mall.repository.ShopRepository;
import com.sommor.bundles.mall.view.SpuFormRenderParam;
import com.sommor.bundles.mall.view.SpuQueryParam;
import com.sommor.bundles.mall.repository.SpuRepository;
import com.sommor.bundles.mall.view.SpuForm;
import com.sommor.bundles.mall.view.SpuTable;
import com.sommor.bundles.user.auth.AuthenticationHolder;
import com.sommor.bundles.user.model.AuthUser;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.core.curd.CurdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/22
 */
@Service
public class SpuService extends CurdService<
        SpuEntity,
        SpuForm,
        SpuFormRenderParam,
        SpuEntity,
        EntityDetailParam,
        SpuTable,
        SpuQueryParam> {

    @Resource
    private SpuRepository spuRepository;

    @Resource
    private ShopRepository shopRepository;

    @Override
    protected void onValidate(SpuEntity entity, SpuEntity originalEntity) {
        super.onValidate(entity, originalEntity);

        if (null != originalEntity) {
            ShopEntity shopEntity = shopRepository.findById(originalEntity.getShopId());
            AuthUser authUser = AuthenticationHolder.getAuthUser();
            if (null == authUser || ! authUser.getUserId().equals(shopEntity.getUserId())) {
                throw new ErrorCodeException(ErrorCode.of("shop.spu.seller.invalid", originalEntity.getShopId()));
            }
        }
    }
}
