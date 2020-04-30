package com.sommor.bundles.outline.service;

import com.sommor.bundles.outline.entity.OutlineOrderAccessKeyEntity;
import com.sommor.bundles.outline.repository.OutlineOrderAccessKeyRepository;
import com.sommor.core.curd.CurdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@Service
public class OutlineOrderAccessKeyService extends CurdService<OutlineOrderAccessKeyEntity, String> {

    @Resource
    private OutlineOrderAccessKeyRepository outlineOrderAccessKeyRepository;
}
