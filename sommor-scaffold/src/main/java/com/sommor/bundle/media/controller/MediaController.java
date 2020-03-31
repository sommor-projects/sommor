package com.sommor.bundle.media.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.util.Base64Util;
import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.api.response.ApiResponse;
import com.sommor.bundle.media.component.file.MediaFile;
import com.sommor.bundle.media.service.MediaService;
import com.sommor.bundle.aliyun.OcrAdvancedRequest;
import com.sommor.bundle.aliyun.OcrAdvancedService;
import com.sommor.bundle.baiduyun.BaiduYunService;
import com.sommor.core.utils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Resource
    private MediaService mediaService;

    @Resource
    private BaiduYunService baiduYunService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ApiResponse<MediaFile> fileUpload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return ApiResponse.error(ErrorCode.of("media.upload.empty"));
        }

        try {
            InputStream is = file.getInputStream();
            MediaFile mediaFile = mediaService.upload(is, file.getOriginalFilename());
            return ApiResponse.success(mediaFile);
        } catch (IOException e) {
            throw new ErrorCodeException(ErrorCode.of("media.upload.exception", e.getMessage()));
        }
    }


    @Resource
    private OcrAdvancedService ocrAdvancedService;

    @RequestMapping(value = "/ocr/aliyun", method = RequestMethod.POST)
    public ApiResponse<String> aliyunOcr(@RequestParam(value = "file") MultipartFile file) {

        try {
            InputStream is = file.getInputStream();
            String base64Content = Base64Util.encode(Converter.convertBytes(is));
            OcrAdvancedRequest request = new OcrAdvancedRequest();
            request.setImg(base64Content);
            String s = ocrAdvancedService.identify(request);
            return ApiResponse.success(s);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/ocr", method = RequestMethod.POST)
    public ApiResponse<String> ocr(@RequestParam(value = "file") MultipartFile file) {

        try {
            InputStream is = file.getInputStream();
            JSONObject jsonObject = baiduYunService.identity(Converter.convertBytes(is));
            JSONArray a = jsonObject.getJSONArray("words_result");
            if (null != a) {
                List<String> words = new ArrayList<>();
                for (int i=0; i<a.size(); i++) {
                    JSONObject o = a.getJSONObject(i);
                    String s = o.getString("words");
                    if (null != s) {
                        s = s.trim();
                        if (StringUtils.isNotBlank(s) && StringUtils.isAlphanumeric(s)) {
                            words.add(s);
                        }
                    }
                }
            }
            return ApiResponse.success(jsonObject.toString());
        } catch (IOException e) {
            throw new ErrorCodeException(ErrorCode.of("media.upload.exception", e.getMessage()));
        }
    }
}
