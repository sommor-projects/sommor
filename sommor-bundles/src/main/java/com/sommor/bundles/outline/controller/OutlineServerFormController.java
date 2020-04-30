package com.sommor.bundles.outline.controller;

import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.bundles.outline.model.OutlineServerForm;
import com.sommor.core.component.form.FormController;
import com.sommor.core.scaffold.param.EntityFormParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@RestController
@RequestMapping(value = "/api/outline-server")
public class OutlineServerFormController extends FormController<OutlineServerEntity, OutlineServerForm, EntityFormParam> {
}
