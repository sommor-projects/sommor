package com.sommor.bundles.taxonomy.controller;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.core.curd.delete.EntityDeleteController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
@RestController
@RequestMapping(value = "/api/taxonomy")
public class TaxonomyDeleteController extends EntityDeleteController<TaxonomyEntity> {
}
