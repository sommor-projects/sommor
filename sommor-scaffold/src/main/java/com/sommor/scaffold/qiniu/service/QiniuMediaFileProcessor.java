package com.sommor.scaffold.qiniu.service;

import com.sommor.extensibility.config.Implement;
import com.sommor.bundles.media.model.UploadedFile;
import com.sommor.bundles.media.service.MediaFileProcessor;
import com.sommor.scaffold.qiniu.QiniuClient;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Implement
public class QiniuMediaFileProcessor implements MediaFileProcessor {

    @Resource
    private QiniuClient qiniuClient;

    @Override
    public UploadedFile upload(InputStream is, String originalFileName) {
        return qiniuClient.upload(is, originalFileName);
    }

    @Override
    public String parseUrl(String uri) {
        return qiniuClient.resolveUrl(uri);
    }
}
