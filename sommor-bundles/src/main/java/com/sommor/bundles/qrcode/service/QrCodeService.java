package com.sommor.bundles.qrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sommor.bundles.qrcode.entity.QrCodeEntity;
import com.sommor.bundles.qrcode.repository.QrCodeRepository;
import com.sommor.core.curd.CurdService;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.core.utils.Encryption;
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
public class QrCodeService extends CurdService<QrCodeEntity> {

    public static final String QR_CODE_KEY = "qrCode";

    @Resource
    private QrCodeRepository qrCodeRepository;

    public String generateQrCode() {
        String uuid = UUID.randomUUID().toString();
        return Encryption.md5(uuid);
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
            return "data:image/png;base64," + Base64.encode(os.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public QrCodeEntity queryByCode(String code) {
        return this.qrCodeRepository.findByCode(code);
    }

    public QrCodeEntity deleteByCode(String code) {
        QrCodeEntity entity = this.qrCodeRepository.findByCode(code);
        if (null != entity) {
            this.delete(entity);
        }

        return entity;
    }

    public String getEntityQrCode(BaseEntity entity) {
        if (entity.hasField(QR_CODE_KEY)) {
            return entity.getFieldValue(QR_CODE_KEY);
        }

        return null;
    }

    public boolean enrichEntityQrCode(BaseEntity entity) {
        if (entity.hasField(QR_CODE_KEY)) {
            String qrCode = entity.getFieldValue(QR_CODE_KEY);
            if (StringUtils.isBlank(qrCode)) {
                entity.setFieldValue(QR_CODE_KEY, this.generateQrCode());
                return true;
            }
        }

        return false;
    }

    @Override
    public QrCodeEntity onGetOriginalEntity(QrCodeEntity entity) {
        QrCodeEntity original = super.onGetOriginalEntity(entity);
        if (null == original) {
            String code = entity.getCode();
            if (StringUtils.isNotBlank(code)) {
                original = queryByCode(code);
                if (null != original) {
                    entity.setId(original.getId());
                }
            }
        }

        return original;
    }
}
