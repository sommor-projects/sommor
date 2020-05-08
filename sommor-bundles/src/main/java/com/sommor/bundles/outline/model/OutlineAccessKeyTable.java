package com.sommor.bundles.outline.model;

import com.sommor.core.component.bytes.BytesConvert;
import com.sommor.core.component.status.StatusField;
import com.sommor.mybatis.entity.config.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@Getter
@Setter
public class OutlineAccessKeyTable {

    private String id;

    private String outlineServerId;

    private String name;

    private String accessId;

    private String accessUrl;

    @StatusField
    private AccessKeyStatus status;

    @BytesConvert(field = "usageBytes", autoUnit = true)
    private String usage;
}
