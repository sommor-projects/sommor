package com.sommor.bundles.qrcode.service;

import com.sommor.bundles.qrcode.entity.QrCodeEntity;
import com.sommor.bundles.qrcode.model.QrCodeForm;
import com.sommor.core.component.form.EntityFormService;
import com.sommor.core.curd.CurdManager;
import com.sommor.core.model.Model;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.core.scaffold.param.EntityFormParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/29
 */
@Service
public class QrCodeFormService extends EntityFormService<QrCodeEntity, QrCodeForm, EntityFormParam> {

    @Resource
    private QrCodeService qrCodeService;

    @Override
    protected void onFormSaving(Model model, QrCodeEntity entity, QrCodeEntity originalEntity) {
        super.onFormSaving(model, entity, originalEntity);

        if ((null == entity.getId() || entity.getId() == 0) && StringUtils.isBlank(entity.getCode())) {
            entity.setCode(qrCodeService.generateQrCode());

            if (StringUtils.isNotBlank(entity.getSubject())) {
                CurdRepository subjectRepository = CurdManager.getCurdRepository(entity.getSubject());
                BaseEntity subjectEntity = (BaseEntity) subjectRepository.findById(entity.getSubjectId());
                if (null != subjectEntity) {
                    if (qrCodeService.enrichEntityQrCode(subjectEntity)) {
                        subjectRepository.update(subjectEntity);
                    }
                }
            }

        }
    }
}
