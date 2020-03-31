package com.sommor.bundle.qrcode.controller;

import com.sommor.bundle.qrcode.entity.QrCodeEntity;
import com.sommor.bundle.qrcode.model.QrCodeForm;
import com.sommor.bundle.qrcode.service.QrCodeFormService;
import com.sommor.bundle.user.config.Authority;
import com.sommor.component.form.FormController;
import com.sommor.scaffold.param.EntityFormParam;
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
