package com.sommor.bundles.outline.controller;

import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.core.curd.delete.EntityDeleteController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/8
 */
@RestController
@RequestMapping(value = "/api/outline-server")
public class OutlineServerDeleteController extends EntityDeleteController<OutlineServerEntity> {
}
