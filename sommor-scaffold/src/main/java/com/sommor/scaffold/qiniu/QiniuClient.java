package com.sommor.scaffold.qiniu;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.extensibility.config.Implement;
import com.sommor.bundle.media.model.UploadedFile;
import com.sommor.scaffold.qiniu.model.QiniuUploadResult;
import com.sommor.scaffold.qiniu.settings.QiniuSettings;
import com.sommor.launcher.AppLauncher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.io.InputStream;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Slf4j
@Implement
public class QiniuClient implements AppLauncher {

    public static final String URL_KEY_PREFIX = "qiniu://";

    private Auth auth;
    private UploadManager uploadManager;
    private StringMap putPolicy;

    private void initUploadManager() {
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        this.uploadManager = uploadManager;

        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":$(key),\"hash\":$(etag),\"fileSize\":$(fsize),\"mimeType\":$(mimeType)}");
        this.putPolicy = putPolicy;
    }

    private void initAuth(String accessKey, String secretKey) {
        Auth auth = Auth.create(accessKey, secretKey);
        this.auth = auth;
    }

    public String resolveUrl(String url) {
        if (null != url && url.startsWith(QiniuClient.URL_KEY_PREFIX)) {
            String k = url.substring(QiniuClient.URL_KEY_PREFIX.length());
            return QiniuSettings.urlDomain + k;
        }

        return null;
    }

    private String parseToken() {
        return this.auth.uploadToken(QiniuSettings.bucket, null, 3600, this.putPolicy);
    }

    public UploadedFile upload(InputStream is, String fileName) {
        try {
            Response response= this.uploadManager.put(is, null, this.parseToken(), null, null);
            if (response.isOK()) {
                log.debug("qiniu upload response: ", response.bodyString());
                QiniuUploadResult result = JSON.parseObject(response.bodyString(), QiniuUploadResult.class);
                String key = URL_KEY_PREFIX + result.getKey();
                String url = QiniuSettings.urlDomain + result.getKey();
                UploadedFile file = new UploadedFile();
                file.setUri(key);
                file.setUrl(url);
                file.setMimeType(result.getMimeType());
                file.setFileSize(result.getFileSize());
                return file;
            } else {
                throw new ErrorCodeException(ErrorCode.of("qinui.upload.failed", response.error));
            }
        } catch (QiniuException e) {
            log.error("qiniu upload exception", e);
            throw new ErrorCodeException(ErrorCode.of("qinui.upload.exception", e.getMessage()));
        }
    }

    @Override
    public void onLaunched(ApplicationContext context) {
        initAuth(QiniuSettings.accessKey, QiniuSettings.secureKey);
        initUploadManager();
    }
}
