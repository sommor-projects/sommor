package com.sommor.bundle.media.service;

import com.sommor.extensibility.config.Extension;
import com.sommor.bundle.media.model.UploadedFile;

import java.io.InputStream;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Extension(name = "多媒体文件上传管理器")
public interface MediaFileProcessor {

    String parseUrl(String uri);

    UploadedFile upload(InputStream is, String originalFileName);

}
