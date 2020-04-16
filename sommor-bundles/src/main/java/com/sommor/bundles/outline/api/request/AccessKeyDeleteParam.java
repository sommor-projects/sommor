package com.sommor.bundles.outline.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@Getter
@Setter
public class AccessKeyDeleteParam {

    @NotBlank
    private String id;

}
