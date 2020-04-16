package com.sommor.bundles.outline.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@Getter
@Setter
public class ListAccessKeysResponse {

    private List<AccessKey> accessKeys;

}
