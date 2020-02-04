package com.sommor.bundles.qrcode.fields;

import com.sommor.bundles.qrcode.service.QrCodeService;
import com.sommor.bundles.qrcode.view.QrCodeForm;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.scaffold.entity.configurable.ConfigurableEntity;
import com.sommor.core.view.field.FieldContext;
import com.sommor.core.view.field.FieldProcessor;
import com.sommor.core.view.field.FieldSaveContext;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@Implement
public class QrCodeFieldProcessor implements FieldProcessor<QrCodeField> {

    public static final String QR_CODE_KEY = "qrCode";

    @Resource
    private QrCodeService qrCodeService;

    @Override
    public Object processOnFill(QrCodeField qrCodeField, FieldContext ctx) {
        Object target = ctx.getData().getTarget();
        if (target instanceof ConfigurableEntity) {
            return ((ConfigurableEntity) target).getConfig().getString(QR_CODE_KEY);
        }
        return null;
    }

    @Override
    public void processOnFormSaving(QrCodeField qrCodeField, FieldSaveContext ctx) {
        if (null == ctx.getOriginal()) {
            BaseEntity baseEntity = ctx.getEntity();
            if (baseEntity instanceof ConfigurableEntity) {
                ((ConfigurableEntity) baseEntity).addConfig(QR_CODE_KEY, qrCodeService.generateQrCode());
            }
        }
    }

    @Override
    public void processOnFormSaved(QrCodeField qrCodeField, FieldSaveContext ctx) {
        if (null == ctx.getOriginal()) {
            BaseEntity baseEntity = ctx.getEntity();
            if (baseEntity instanceof ConfigurableEntity) {
                String code = ((ConfigurableEntity) baseEntity).getConfig().getString(QR_CODE_KEY);

                QrCodeForm qrCodeForm = new QrCodeForm();
                qrCodeForm.setSubject(baseEntity.definition().getSubjectName());
                qrCodeForm.setSubjectId(baseEntity.getId());
                qrCodeForm.setCode(code);

                qrCodeService.saveForm(qrCodeForm);
            }
        }
    }
}
