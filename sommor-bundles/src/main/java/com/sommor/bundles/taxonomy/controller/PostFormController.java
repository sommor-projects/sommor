package com.sommor.bundles.taxonomy.controller;

import com.sommor.bundles.taxonomy.entity.PostEntity;
import com.sommor.bundles.taxonomy.model.PostForm;
import com.sommor.core.component.form.FormController;
import com.sommor.core.scaffold.param.EntityFormParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
@RestController
@RequestMapping(value = "/api/post")
public class PostFormController extends FormController<PostEntity, PostForm, EntityFormParam> {

}
