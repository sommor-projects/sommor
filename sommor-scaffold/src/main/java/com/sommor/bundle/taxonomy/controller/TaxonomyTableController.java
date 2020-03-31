package com.sommor.bundle.taxonomy.controller;

import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.model.TaxonomyTable;
import com.sommor.bundle.taxonomy.model.TaxonomyQueryParam;
import com.sommor.component.table.TableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
@RestController
@RequestMapping(value = "/api/taxonomy")
//@Authority(roles = {"admin"})
public class TaxonomyTableController extends TableController<TaxonomyEntity, TaxonomyTable, TaxonomyQueryParam> {

}
