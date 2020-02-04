package com.sommor.bundles.qrcode.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/31
 */
public class QrCodeImageParam {

    @Getter
    @Setter
    @NotBlank
    private String code;

    @Getter
    @Setter
    @NotNull
    private Integer width;

    @Getter
    @Setter
    @NotNull
    private Integer height;

}
