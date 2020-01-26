package com.sommor.bundles.mall.service;

import com.sommor.bundles.mall.entity.SpuEntity;
import com.sommor.bundles.mall.view.SpuFormRenderParam;
import com.sommor.bundles.mall.view.SpuQueryParam;
import com.sommor.bundles.mall.repository.SpuRepository;
import com.sommor.bundles.mall.view.SpuForm;
import com.sommor.bundles.mall.view.SpuTable;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.scaffold.service.CurdService;
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
}
