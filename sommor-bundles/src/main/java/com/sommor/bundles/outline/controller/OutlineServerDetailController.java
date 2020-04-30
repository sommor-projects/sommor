package com.sommor.bundles.outline.controller;

import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.bundles.outline.model.OutlineServer;
import com.sommor.core.component.detail.DetailController;
import com.sommor.core.scaffold.param.EntityDetailParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@RestController
@RequestMapping(value = "/api/outline-server")
public class OutlineServerDetailController extends DetailController<OutlineServerEntity, OutlineServer, EntityDetailParam> {
}
