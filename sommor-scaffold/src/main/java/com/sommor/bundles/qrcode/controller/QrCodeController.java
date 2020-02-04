package com.sommor.bundles.qrcode.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.bundles.qrcode.entity.QrCodeEntity;
import com.sommor.bundles.qrcode.service.QrCodeService;
import com.sommor.bundles.qrcode.view.QrCodeForm;
import com.sommor.bundles.qrcode.view.QrCodeImageParam;
import com.sommor.bundles.qrcode.view.QrCodeTable;
import com.sommor.bundles.user.config.Authority;
import com.sommor.core.curd.CurdController;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.param.EntityQueryParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/29
 */
@RestController
@RequestMapping(value = "/api/qrcode")
@Authority(roles = {"admin"})
public class QrCodeController extends CurdController<
        QrCodeEntity,
        QrCodeForm,
        EntityFormRenderParam,
        QrCodeEntity,
        EntityDetailParam,
        QrCodeTable,
        EntityQueryParam> {

    @Resource
    private QrCodeService qrCodeService;

    @ApiOperation(value = "二维码图片(base64)")
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<String> renderForm(@Validated QrCodeImageParam param) {
        String image = qrCodeService.generateQrImage(param.getCode(), param.getWidth(), param.getHeight());
        return ApiResponse.success(image);
    }

}
