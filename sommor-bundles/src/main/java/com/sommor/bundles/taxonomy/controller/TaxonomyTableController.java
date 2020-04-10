package com.sommor.bundles.taxonomy.controller;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.model.TaxonomyTable;
import com.sommor.bundles.taxonomy.model.TaxonomyQueryParam;
import com.sommor.core.component.table.TableController;
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
