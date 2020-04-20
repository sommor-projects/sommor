package com.sommor.bundles.qrcode.entity;

import com.sommor.core.scaffold.entity.timed.TimedEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table("qr_codes")
@Getter
@Setter
public class QrCodeEntity extends TimedEntity<Long> {

    @Column
    private String code;

    @Column
    private String subject;

    @Column
    private Long subjectId;

    @Column
    private Long userId;
}
