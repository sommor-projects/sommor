package com.sommor.bundle.media.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Getter
@Setter
public class UploadedFile {

    private String uri;

    private String url;

    private String mimeType;

    private Integer fileSize;
}
