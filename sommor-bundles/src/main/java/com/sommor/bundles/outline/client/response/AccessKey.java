package com.sommor.bundles.outline.client.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@Getter
@Setter
public class AccessKey {
    private String id;
    private String name;
    private String password;
    private Integer port;
    private String method;
    private String accessUrl;
}
