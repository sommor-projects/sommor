package com.sommor.bundles.outline.controller;

import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.model.OutlineAccessKeyForm;
import com.sommor.core.component.form.FormController;
import com.sommor.core.scaffold.param.EntityFormParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@RestController
@RequestMapping(value = "/api/outline-accesskey")
public class OutlineAccessKeyFormController extends FormController<OutlineAccessKeyEntity, OutlineAccessKeyForm, EntityFormParam> {
}
