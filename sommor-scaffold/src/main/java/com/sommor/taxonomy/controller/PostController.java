package com.sommor.taxonomy.controller;

import com.sommor.scaffold.controller.CurdController;
import com.sommor.scaffold.param.EntityInfoParam;
import com.sommor.taxonomy.entity.PostEntity;
import com.sommor.taxonomy.form.PostForm;
import com.sommor.taxonomy.model.PostListItem;
import com.sommor.taxonomy.param.SubjectFormRenderParam;
import com.sommor.taxonomy.param.PostQueryParam;
import com.sommor.usercenter.config.Authority;
import org.springframework.web.bind.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
@RestController
@RequestMapping(value = "/api/post")
@Authority(roles = {"admin"})
public class PostController extends CurdController<
        PostEntity,
        PostForm,
        SubjectFormRenderParam,
        PostEntity,
        EntityInfoParam,
        PostListItem,
        PostQueryParam> {

}
