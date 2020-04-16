package com.sommor.bundles.outline.api.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@Getter
@Setter
public class ServerInfo {
    private String name;
    private String  serverId;
    private Boolean metricsEnabled;
    private Long createdTimestampMs;
    private Integer portForNewAccessKeys;
}
