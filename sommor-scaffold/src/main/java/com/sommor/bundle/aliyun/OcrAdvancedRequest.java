package com.sommor.bundle.aliyun;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */
@Getter
@Setter
public class OcrAdvancedRequest {

    private String img;

    private String url;

    private Boolean prob;

    private Boolean charInfo;

    private Boolean rotate;

    private Boolean table;
}
