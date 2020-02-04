package com.sommor.bundles.mall.service;

import com.sommor.bundles.mall.entity.ShopEntity;
import com.sommor.bundles.mall.view.ShopQueryParam;
import com.sommor.bundles.mall.repository.ShopRepository;
import com.sommor.bundles.mall.view.ShopDetail;
import com.sommor.bundles.mall.view.ShopForm;
import com.sommor.bundles.mall.view.ShopTable;
import com.sommor.bundles.taxonomy.view.SubjectFormRenderParam;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.core.curd.CurdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
@Service
public class ShopService extends CurdService<
        ShopEntity,
        ShopForm,
        SubjectFormRenderParam,
        ShopDetail,
        EntityDetailParam,
        ShopTable,
        ShopQueryParam> {

    @Resource
    private ShopRepository shopRepository;

}
