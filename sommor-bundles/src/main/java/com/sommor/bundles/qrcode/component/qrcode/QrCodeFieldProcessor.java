package com.sommor.bundles.qrcode.component.qrcode;

import com.sommor.bundles.qrcode.service.QrCodeFormService;
import com.sommor.bundles.qrcode.service.QrCodeService;
import com.sommor.bundles.qrcode.model.QrCodeForm;
import com.sommor.core.component.form.FieldSaveContext;
import com.sommor.core.component.form.extension.FormFieldSavedProcessor;
import com.sommor.core.component.form.extension.FormFieldSavingProcessor;
import com.sommor.core.utils.Converter;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.mybatis.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@Implement
public class QrCodeFieldProcessor implements
        FieldFillProcessor<QrCodeFieldConfig>,
        FormFieldSavingProcessor<QrCodeFieldConfig>,
        FormFieldSavedProcessor<QrCodeFieldConfig> {

    @Resource
    private QrCodeService qrCodeService;

    @Resource
    private QrCodeFormService qrCodeFormService;

    @Override
    public Object processOnFieldFill(QrCodeFieldConfig config, com.sommor.core.model.fill.FieldFillContext ctx) {
        Object target = ctx.getSourceModel().getTarget();
        if (target instanceof BaseEntity) {
            return qrCodeService.getEntityQrCode((BaseEntity) target);
        }

        return null;
    }

    @Override
    public void processOnFormSaved(QrCodeFieldConfig qrCodeFieldConfig, FieldSaveContext ctx) {
        BaseEntity baseEntity = ctx.getEntity();
        String qrCode = qrCodeService.getEntityQrCode(baseEntity);
        if (StringUtils.isNotBlank(qrCode)) {;
            QrCodeForm qrCodeForm = new QrCodeForm();
            qrCodeForm.setSubject(baseEntity.definition().getSubjectName());
            qrCodeForm.setSubjectId(Converter.toString(baseEntity.getId()));
            qrCodeForm.setCode(qrCode);
            qrCodeFormService.saveEntityForm(qrCodeForm);
        }
    }

    @Override
    public void processOnFormSaving(QrCodeFieldConfig qrCodeFieldConfig, com.sommor.core.component.form.FieldSaveContext ctx) {
        qrCodeService.enrichEntityQrCode(ctx.getEntity());
    }
}
