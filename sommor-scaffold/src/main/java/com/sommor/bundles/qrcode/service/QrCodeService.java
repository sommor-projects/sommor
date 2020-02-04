package com.sommor.bundles.qrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sommor.bundles.qrcode.entity.QrCodeEntity;
import com.sommor.bundles.qrcode.fields.QrCodeFieldProcessor;
import com.sommor.bundles.qrcode.repository.QrCodeRepository;
import com.sommor.bundles.qrcode.view.QrCodeForm;
import com.sommor.bundles.qrcode.view.QrCodeTable;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.scaffold.entity.configurable.ConfigurableEntity;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.param.EntityQueryParam;
import com.sommor.core.curd.CurdManager;
import com.sommor.core.curd.CurdService;
import com.sommor.core.utils.Encryption;
import com.sommor.core.view.field.Form;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/29
 */
@Service
public class QrCodeService extends CurdService<
        QrCodeEntity,
        QrCodeForm,
        EntityFormRenderParam,
        QrCodeEntity,
        EntityDetailParam,
        QrCodeTable,
        EntityQueryParam> {

    @Resource
    private QrCodeRepository qrCodeRepository;

    public String generateQrCode() {
        String uuid = UUID.randomUUID().toString();
        String code = Encryption.md5(uuid);
        return code;
    }

    @Override
    protected void onFormSaving(Form form, QrCodeEntity entity, QrCodeEntity originalEntity) {
        super.onFormSaving(form, entity, originalEntity);

        if ((null == entity.getId() || entity.getId() == 0) && StringUtils.isBlank(entity.getCode())) {
            entity.setCode(this.generateQrCode());

            if (StringUtils.isNotBlank(entity.getSubject())) {
                CurdRepository subjectRepository = CurdManager.getCurdRepository(entity.getSubject());
                BaseEntity subjectEntity = subjectRepository.findById(entity.getSubjectId());
                if (subjectEntity instanceof ConfigurableEntity) {
                    ((ConfigurableEntity) subjectEntity).addConfig(QrCodeFieldProcessor.QR_CODE_KEY, entity.getCode());
                    subjectRepository.save(subjectEntity);
                }
            }

        }
    }

    public String generateQrImage(String code, int width, int height) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 指定字符编码为“utf-8”
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 指定二维码的纠错等级为中级
        hints.put(EncodeHintType.MARGIN, 2); // 设置图片的边距

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(code, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(bufferedImage, "png", os);
            String image = new String("data:image/png;base64," + Base64.encode(os.toByteArray()));
            return image;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected QrCodeEntity onGetOriginalEntity(Form form, QrCodeEntity entity) {
        QrCodeEntity original = super.onGetOriginalEntity(form, entity);
        if (null == original) {
            String code = entity.getCode();
            if (StringUtils.isNotBlank(code)) {
                original = qrCodeRepository.findByCode(code);
                entity.setId(original.getId());
            }
        }

        return original;
    }

    public QrCodeEntity deleteByCode(String code) {
        QrCodeEntity entity = this.qrCodeRepository.findByCode(code);
        if (null != entity) {
            this.delete(entity);
        }

        return entity;
    }
}
