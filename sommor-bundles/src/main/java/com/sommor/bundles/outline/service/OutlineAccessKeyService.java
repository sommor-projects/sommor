package com.sommor.bundles.outline.service;

import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.repository.OutlineAccessKeyRepository;
import com.sommor.core.curd.CurdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/25
 */
@Service
public class OutlineAccessKeyService extends CurdService<OutlineAccessKeyEntity, String> {

    @Resource
    private OutlineAccessKeyRepository outlineAccessKeyRepository;


    public List<OutlineAccessKeyEntity> findByServerId(String outlineServerId) {
        return outlineAccessKeyRepository.findByServerId(outlineServerId);
    }
}
