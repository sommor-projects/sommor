package com.sommor.scaffold.qiniu.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Getter
@Setter
public class QiniuUploadResult {

    private String key;

    private String mimeType;

    private Integer fileSize;
}
