package com.sommor.core.component.bytes;

import com.sommor.core.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/3
 */
@Getter
@Setter
public class BytesConvertConfig extends TargetConfig {

    private Boolean autoUnit;

    private String field;

    private ByteUnit sourceUnit;

    private ByteUnit targetUnit;

}
