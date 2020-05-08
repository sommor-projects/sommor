package com.sommor.bundles.outline.controller;

import com.sommor.bundles.outline.client.request.AccessKeyDeleteParam;
import com.sommor.bundles.outline.client.response.AccessKey;
import com.sommor.bundles.outline.client.OutlineServerClient;
import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.bundles.outline.model.OutlineAccessKeyCreateParam;
import com.sommor.bundles.outline.model.OutlineServerBytesTransferredParam;
import com.sommor.bundles.outline.model.OutlineServerRenameParam;
import com.sommor.bundles.outline.model.OutlineServerSyncParam;
import com.sommor.bundles.outline.service.OutlineServerService;
import com.sommor.core.api.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@RestController
@RequestMapping(value = "/api/outline/server")
public class OutlineServerController {

    private OutlineServerClient outlineServerClient = new OutlineServerClient("https://47.240.36.86:12375/sNoll--1uJqvWLr3h9dy5Q");

    @Resource
    private OutlineServerService outlineServerService;

    @ApiOperation(value = "accessKey列表")
    @RequestMapping(value = "/access-keys", method = RequestMethod.GET)
    public ApiResponse<List<OutlineAccessKeyEntity>> accessKeys(String serverId) {
        List<OutlineAccessKeyEntity> entities = outlineServerService.findAccessKeys(serverId);
        return ApiResponse.success(entities);
    }

    @ApiOperation(value = "Outline Server改名")
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public ApiResponse<OutlineServerEntity> serverRename(@Validated OutlineServerRenameParam param) {
        OutlineServerEntity entity = outlineServerService.renameServer(param);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "创建AccessKey")
    @RequestMapping(value = "/access-keys", method = RequestMethod.POST)
    public ApiResponse<AccessKey> createAccessKey(OutlineAccessKeyCreateParam param) {
        AccessKey accessKey = outlineServerClient.createAccessKey();
        return ApiResponse.success(accessKey);
    }

    @ApiOperation(value = "删除AccessKey")
    @RequestMapping(value = "/access-keys/delete", method = RequestMethod.POST)
    public ApiResponse<AccessKey> deleteAccessKey(@Validated AccessKeyDeleteParam param) {
        outlineServerClient.deleteAccessKey(param.getId());
        return ApiResponse.success();
    }

    @ApiOperation(value = "bytes transferred per access key")
    @RequestMapping(value = "/metrics/transfer", method = RequestMethod.POST)
    public ApiResponse<Map<String, Long>> getBytesTransferred(OutlineServerBytesTransferredParam param) {
        Map<String, Long> bytes = outlineServerClient.getBytesTransferred();
        return ApiResponse.success(bytes);
    }

    @ApiOperation(value = "同步Outline Server")
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public ApiResponse accessKeys(@RequestBody OutlineServerSyncParam param) {
        outlineServerService.syncServer(param.getServerId());
        return ApiResponse.success();
    }
}
