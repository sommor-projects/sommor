package com.sommor.bundles.qrcode.controller;

import com.sommor.bundles.qrcode.entity.QrCodeEntity;
import com.sommor.bundles.qrcode.model.QrCodeForm;
import com.sommor.bundles.qrcode.service.QrCodeFormService;
import com.sommor.bundles.user.config.Authority;
import com.sommor.core.component.form.FormController;
import com.sommor.core.scaffold.param.EntityFormParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author yanguanwei@qq.com
 * @since 2020/1/29
 */
@RestController
@RequestMapping(value = "/api/qrcode")
@Authority(roles = {"admin"})
public class QrCodeFormController extends FormController<
        QrCodeEntity,
        QrCodeForm,
        EntityFormParam> {

    public QrCodeFormController(QrCodeFormService formService) {
        super(formService);
    }
}
