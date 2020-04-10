package com.sommor.bundles.qrcode.model;

import com.sommor.core.model.target.EntityForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/29
 */
public class QrCodeForm extends EntityForm {

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private Integer subjectId;
}
