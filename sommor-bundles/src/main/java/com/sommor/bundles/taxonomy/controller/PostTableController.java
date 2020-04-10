package com.sommor.bundles.taxonomy.controller;

import com.sommor.bundles.taxonomy.entity.PostEntity;
import com.sommor.bundles.taxonomy.model.PostTable;
import com.sommor.bundles.taxonomy.model.PostQueryParam;
import com.sommor.core.component.table.TableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
@RestController
@RequestMapping(value = "/api/post")
//@Authority(roles = {"admin"})
public class PostTableController extends TableController<PostEntity, PostTable, PostQueryParam> {

}
