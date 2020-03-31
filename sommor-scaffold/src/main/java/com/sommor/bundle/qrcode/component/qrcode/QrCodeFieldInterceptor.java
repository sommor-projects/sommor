package com.sommor.bundle.qrcode.component.qrcode;

import com.sommor.bundle.qrcode.service.QrCodeService;
import com.sommor.curd.delete.EntityDeleteInterceptor;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@Implement
public class QrCodeFieldInterceptor implements EntityDeleteInterceptor {

    @Resource
    private QrCodeService qrCodeService;

    @Override
    public void interceptOnEntityDelete(BaseEntity entity) {
        String qrCode = qrCodeService.getEntityQrCode(entity);
        if (StringUtils.isNotBlank(qrCode)) {
            this.qrCodeService.deleteByCode(qrCode);
        }
    }
}
