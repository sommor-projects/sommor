package com.sommor.bundle.taxonomy.controller;

import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.model.TaxonomyDetail;
import com.sommor.bundle.taxonomy.model.TaxonomyDetailParam;
import com.sommor.component.detail.DetailController;
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
