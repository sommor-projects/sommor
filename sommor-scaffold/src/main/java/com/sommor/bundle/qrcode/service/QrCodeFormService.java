package com.sommor.bundle.qrcode.service;

import com.sommor.bundle.qrcode.entity.QrCodeEntity;
import com.sommor.bundle.qrcode.model.QrCodeForm;
import com.sommor.component.form.FormService;
import com.sommor.curd.CurdManager;
import com.sommor.model.Model;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.scaffold.param.EntityFormParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/29
 */
@Service
public class QrCodeFormService extends FormService<QrCodeEntity, QrCodeForm, EntityFormParam> {

    @Resource
    private QrCodeService qrCodeService;

    @Override
    protected void onFormSaving(Model model, QrCodeEntity entity, QrCodeEntity originalEntity) {
        super.onFormSaving(model, entity, originalEntity);

        if ((null == entity.getId() || entity.getId() == 0) && StringUtils.isBlank(entity.getCode())) {
            entity.setCode(qrCodeService.generateQrCode());

            if (StringUtils.isNotBlank(entity.getSubject())) {
                CurdRepository subjectRepository = CurdManager.getCurdRepository(entity.getSubject());
                BaseEntity subjectEntity = subjectRepository.findById(entity.getSubjectId());
                if (null != subjectEntity) {
                    if (qrCodeService.enrichEntityQrCode(subjectEntity)) {
                        subjectRepository.save(subjectEntity);
                    }
                }
            }

        }
    }

    @Override
    protected QrCodeEntity onGetOriginalEntity(Model model, QrCodeEntity entity) {
        QrCodeEntity original = super.onGetOriginalEntity(model, entity);
        if (null == original) {
            String code = entity.getCode();
            if (StringUtils.isNotBlank(code)) {
                original = qrCodeService.queryByCode(code);
                if (null != original) {
                    entity.setId(original.getId());
                }
            }
        }

        return original;
    }
}
