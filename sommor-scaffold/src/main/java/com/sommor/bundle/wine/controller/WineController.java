package com.sommor.bundle.wine.controller;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.api.response.ApiResponse;
import com.sommor.bundle.wine.service.WineService;
import com.sommor.bundle.wine.model.WineDetailVO;
import com.sommor.core.utils.Converter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */

@RestController
@RequestMapping("/api/wine")
public class WineController {

    @Resource
    private WineService wineService;

    @RequestMapping(value = "/identify", method = RequestMethod.POST)
    public ApiResponse<WineDetailVO> identifyWine(@RequestParam(value = "file") MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            WineDetailVO wine = wineService.identify(Converter.convertBytes(is));
            return ApiResponse.success(wine);
        } catch (IOException e) {
            throw new ErrorCodeException(ErrorCode.of("media.upload.exception", e.getMessage()));
        }
    }
}
