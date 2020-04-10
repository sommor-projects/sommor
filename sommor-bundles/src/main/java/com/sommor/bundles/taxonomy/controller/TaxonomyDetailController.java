package com.sommor.bundles.taxonomy.controller;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.model.TaxonomyDetail;
import com.sommor.bundles.taxonomy.model.TaxonomyDetailParam;
import com.sommor.core.component.detail.DetailController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/27
 */
@RestController
@RequestMapping(value = "/api/taxonomy")
public class TaxonomyDetailController extends DetailController<TaxonomyEntity, TaxonomyDetail, TaxonomyDetailParam> {

}
