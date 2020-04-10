package com.sommor.bundles.taxonomy.controller;

import com.sommor.core.component.detail.DetailController;
import com.sommor.core.scaffold.param.EntityDetailParam;
import com.sommor.bundles.taxonomy.entity.PostEntity;
import com.sommor.bundles.user.config.Authority;
import org.springframework.web.bind.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
@RestController
@RequestMapping(value = "/api/post")
@Authority(roles = {"admin"})
public class PostDetailController extends DetailController<PostEntity, PostEntity, EntityDetailParam> {
}
