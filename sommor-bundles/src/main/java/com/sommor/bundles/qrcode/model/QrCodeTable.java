package com.sommor.bundles.qrcode.model;

import com.sommor.core.component.table.EntityTable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/29
 */
public class QrCodeTable extends EntityTable {

    @Getter
    @Setter
    private String code;


}
