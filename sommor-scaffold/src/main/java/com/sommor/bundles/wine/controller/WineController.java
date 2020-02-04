package com.sommor.bundles.wine.controller;

import com.google.common.collect.Lists;
import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.api.response.ApiResponse;
import com.sommor.bundles.baiduyun.BaiduIdentifiedWine;
import com.sommor.bundles.baiduyun.BaiduYunService;
import com.sommor.bundles.jiukacha.JiuKaChaService;
import com.sommor.bundles.jiukacha.WineResult;
import com.sommor.bundles.jiukacha.WineSearchRequest;
import com.sommor.core.utils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */

@RestController
@RequestMapping("/api/wine")
public class WineController {

    @Resource
    private BaiduYunService baiduYunService;

    @Resource
    private JiuKaChaService jiuKaChaService;

    @RequestMapping(value = "/identify", method = RequestMethod.POST)
    public ApiResponse<WineIdentifyResult> identifyWine(@RequestParam(value = "file") MultipartFile file) {
        WineIdentifyResult result = new WineIdentifyResult();

        try {
            InputStream is = file.getInputStream();
            BaiduIdentifiedWine wine = baiduYunService.identifyWine(Converter.convertBytes(is));
            if (null != wine) {
                String wineTitle = wine.getWineName().getTitle();
                String wineSubTitle = wine.getWineName().getSubTitle();
                if (StringUtils.isNotBlank(wineSubTitle)) {
                    List<WineResult> wineResults = jiuKaChaService.search(WineSearchRequest.of(wineSubTitle));
                    result.setWineResults(wineResults);

                    for (WineResult wineResult : wineResults) {
                        String title = wineResult.getWine().getTitle();
                        String subTitle = wineResult.getWine().getSubTitle();
                        if ((StringUtils.isNotBlank(wineTitle) &&  StringUtils.isNotBlank(title) && title.equalsIgnoreCase(wineTitle))
                         || (StringUtils.isNotBlank(subTitle) && subTitle.equalsIgnoreCase(wineSubTitle))) {
                            if (StringUtils.isBlank(title)) {
                                wineResult.getWine().setTitle(wineTitle);
                            }
                            if (StringUtils.isBlank(subTitle)) {
                                wineResult.getWine().setSubTitle(wineSubTitle);
                            }

                            result.setWineResults(Lists.newArrayList(wineResult));
                            result.setExactMatched(true);

                            break;
                        }
                    }
                }
            }
            return ApiResponse.success(result);
        } catch (IOException e) {
            throw new ErrorCodeException(ErrorCode.of("media.upload.exception", e.getMessage()));
        }
    }
}
