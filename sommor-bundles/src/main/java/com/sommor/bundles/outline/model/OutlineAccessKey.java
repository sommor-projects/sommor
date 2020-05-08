package com.sommor.bundles.outline.model;

import com.sommor.core.component.bytes.BytesConvert;
import com.sommor.core.component.status.StatusField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/3
 */
@Getter
@Setter
public class OutlineAccessKey {

    private String id;

    private String outlineServerId;

    private String accessId;

    private String name;

    private String accessUrl;

    @StatusField
    private AccessKeyStatus status;

    @BytesConvert(field = "usageBytes", autoUnit = true)
    private String usage;
}
