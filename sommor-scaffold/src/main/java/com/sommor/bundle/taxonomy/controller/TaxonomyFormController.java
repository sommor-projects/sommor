package com.sommor.bundle.taxonomy.controller;

import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.model.TaxonomyForm;
import com.sommor.bundle.taxonomy.model.TaxonomyFormParam;
import com.sommor.component.form.FormController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
@RestController
@RequestMapping(value = "/api/taxonomy")
public class TaxonomyFormController extends FormController<TaxonomyEntity, TaxonomyForm, TaxonomyFormParam> {

}
