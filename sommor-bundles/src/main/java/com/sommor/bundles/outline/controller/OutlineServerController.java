package com.sommor.bundles.outline.controller;

import com.sommor.bundles.outline.api.request.AccessKeyDeleteParam;
import com.sommor.bundles.outline.api.request.ServerRenameParam;
import com.sommor.bundles.outline.api.response.AccessKey;
import com.sommor.bundles.outline.api.OutlineServer;
import com.sommor.bundles.outline.api.response.ListAccessKeysResponse;
import com.sommor.bundles.outline.api.response.ServerInfo;
import com.sommor.core.api.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@RestController
@RequestMapping(value = "/api/outline/server")
public class OutlineServerController {

    private OutlineServer outlineServer = new OutlineServer("https://47.240.36.86:12375/sNoll--1uJqvWLr3h9dy5Q");

    @ApiOperation(value = "accessKey list")
    @RequestMapping(value = "/access-keys", method = RequestMethod.GET)
    public ApiResponse<List<AccessKey>> accessKeys() {
        List<AccessKey> response = outlineServer.listAccessKeys();
        return ApiResponse.success(response == null ? null : response);
    }

    @ApiOperation(value = "server info")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResponse<ServerInfo> info() {
        ServerInfo serverInfo = outlineServer.info();
        return ApiResponse.success(serverInfo);
    }

    @ApiOperation(value = "rename server")
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public ApiResponse serverRename(@Validated ServerRenameParam param) {
        outlineServer.rename(param);
        return ApiResponse.success();
    }

    @ApiOperation(value = "create access key")
    @RequestMapping(value = "/access-keys", method = RequestMethod.POST)
    public ApiResponse<AccessKey> createAccessKey() {
        AccessKey accessKey = outlineServer.createAccessKey();
        return ApiResponse.success(accessKey);
    }

    @ApiOperation(value = "delete access key")
    @RequestMapping(value = "/access-keys/delete", method = RequestMethod.POST)
    public ApiResponse<AccessKey> deleteAccessKey(@Validated AccessKeyDeleteParam param) {
        outlineServer.deleteAccessKey(param.getId());
        return ApiResponse.success();
    }

    @ApiOperation(value = "bytes transferred per access key")
    @RequestMapping(value = "/metrics/transfer", method = RequestMethod.POST)
    public ApiResponse<Map<String, Long>> getBytesTransferred() {
        Map<String, Long> bytes = outlineServer.getBytesTransferred();
        return ApiResponse.success(bytes);
    }
}
