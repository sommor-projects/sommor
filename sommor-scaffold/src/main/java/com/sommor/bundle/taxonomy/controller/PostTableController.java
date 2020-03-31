package com.sommor.bundle.taxonomy.controller;

import com.sommor.bundle.taxonomy.entity.PostEntity;
import com.sommor.bundle.taxonomy.model.PostTable;
import com.sommor.bundle.taxonomy.model.PostQueryParam;
import com.sommor.component.table.TableController;
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
