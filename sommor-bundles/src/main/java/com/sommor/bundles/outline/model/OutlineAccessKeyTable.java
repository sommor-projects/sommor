package com.sommor.bundles.outline.model;

import com.sommor.mybatis.entity.config.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@Getter
@Setter
public class OutlineAccessKeyTable {

    private String id;

    private String outlineServerId;

    private String accessId;

    private String name;

    private String method;

    private String accessUrl;

    private Integer status;

    private Long usageBytes;
}
