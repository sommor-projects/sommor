package com.sommor.bundle.qrcode.entity;

import com.sommor.scaffold.entity.timed.TimedEntity;
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
public class QrCodeEntity extends TimedEntity {

    @Column
    private String code;

    @Column
    private String subject;

    @Column
    private Integer subjectId;

    @Column
    private Integer userId;
}
