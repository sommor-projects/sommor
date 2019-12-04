package com.sommor.media.controller;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.api.response.ApiResponse;
import com.sommor.media.model.MediaFileItem;
import com.sommor.media.model.UploadedFile;
import com.sommor.media.service.MediaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Controller
@RequestMapping("/api/media")
public class MediaController {

    @Resource
    private MediaService mediaService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody ApiResponse<MediaFileItem> fileUpload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return ApiResponse.error(ErrorCode.of("media.upload.empty"));
        }

        try {
            InputStream is = file.getInputStream();
            MediaFileItem mediaFileItem = mediaService.upload(is, file.getOriginalFilename());
            return ApiResponse.success(mediaFileItem);
        } catch (IOException e) {
            throw new ErrorCodeException(ErrorCode.of("media.upload.exception", e.getMessage()));
        }
    }
}
