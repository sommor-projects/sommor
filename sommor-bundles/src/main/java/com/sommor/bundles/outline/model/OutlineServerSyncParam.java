package com.sommor.bundles.outline.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Getter
@Setter
public class OutlineServerSyncParam {

    @NotBlank
    private String serverId;

}
