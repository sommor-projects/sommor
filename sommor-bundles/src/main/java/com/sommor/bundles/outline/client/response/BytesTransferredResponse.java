package com.sommor.bundles.outline.client.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@Getter
@Setter
public class BytesTransferredResponse {

    private Map<String, Long> bytesTransferredByUserId;
}
