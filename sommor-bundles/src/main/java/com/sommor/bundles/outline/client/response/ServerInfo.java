package com.sommor.bundles.outline.client.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<AccessKey> accessKeys;
}
