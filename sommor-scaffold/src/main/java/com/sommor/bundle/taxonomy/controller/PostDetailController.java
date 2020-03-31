package com.sommor.bundle.taxonomy.controller;

import com.sommor.component.detail.DetailController;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.bundle.taxonomy.entity.PostEntity;
import com.sommor.bundle.user.config.Authority;
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
