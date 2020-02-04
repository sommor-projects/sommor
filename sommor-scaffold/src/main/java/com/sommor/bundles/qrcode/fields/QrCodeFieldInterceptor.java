package com.sommor.bundles.qrcode.fields;

import com.sommor.bundles.qrcode.service.QrCodeService;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.sql.field.type.Config;
import com.sommor.scaffold.entity.configurable.ConfigurableEntity;
import com.sommor.core.view.field.FieldInterceptor;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@Implement
public class QrCodeFieldInterceptor implements FieldInterceptor {

    @Resource
    private QrCodeService qrCodeService;

    @Override
    public void interceptOnDelete(BaseEntity entity) {
        if (entity instanceof ConfigurableEntity) {
            Config config = ((ConfigurableEntity) entity).getConfig();
            if (null != config) {
                String code = config.getString(QrCodeFieldProcessor.QR_CODE_KEY);
                if (StringUtils.isNotBlank(code)) {
                    this.qrCodeService.deleteByCode(code);
                }
            }
        }
    }
}
