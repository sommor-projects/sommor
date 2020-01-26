package com.sommor.bundles.taxonomy.controller;

import com.sommor.scaffold.controller.CurdController;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.bundles.taxonomy.entity.PostEntity;
import com.sommor.bundles.taxonomy.view.PostForm;
import com.sommor.bundles.taxonomy.view.PostTable;
import com.sommor.bundles.taxonomy.view.SubjectFormRenderParam;
import com.sommor.bundles.taxonomy.view.PostQueryParam;
import com.sommor.bundles.user.config.Authority;
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
        EntityDetailParam,
        PostTable,
        PostQueryParam> {

}
