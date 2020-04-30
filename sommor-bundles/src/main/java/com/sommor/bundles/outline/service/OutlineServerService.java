package com.sommor.bundles.outline.service;

import com.sommor.bundles.mall.order.model.Order;
import com.sommor.bundles.outline.api.OutlineServerApi;
import com.sommor.bundles.outline.api.request.ServerRenameParam;
import com.sommor.bundles.outline.api.response.AccessKey;
import com.sommor.bundles.outline.api.response.ServerInfo;
import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.bundles.outline.model.OutlineServer;
import com.sommor.bundles.outline.model.OutlineServerRenameParam;
import com.sommor.bundles.outline.repository.OutlineServerRepository;
import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.curd.CurdService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@Service
public class OutlineServerService extends CurdService<OutlineServerEntity, String> {

    @Resource
    private OutlineServerRepository outlineServerRepository;

    @Resource
    private OutlineAccessKeyService outlineAccessKeyService;

    public ServerInfo syncOutlineServer(String apiUrl) {
        OutlineServerApi outlineServerApi = new OutlineServerApi(apiUrl);
        ServerInfo serverInfo = outlineServerApi.info();
        String serverId = serverInfo.getServerId();

        List<AccessKey> accessKeys = processOutlineServerAccessKeys(serverId, outlineServerApi);
        serverInfo.setAccessKeys(accessKeys);

        return serverInfo;
    }

    public List<OutlineAccessKeyEntity> findAccessKeys(String serverId) {
        return outlineAccessKeyService.findByServerId(serverId);
    }

    public OutlineServerEntity renameServer(OutlineServerRenameParam param) {
        OutlineServerEntity serverEntity = outlineServerRepository.findById(param.getServerId());
        if (null != serverEntity) {
            serverEntity.setName(param.getName());
            outlineServerRepository.update(serverEntity);
        }
        return serverEntity;
    }

    public OutlineAccessKeyEntity createOutlineAccessKey(String serverId, String name) {
        OutlineServerEntity serverEntity = outlineServerRepository.findById(serverId);
        if (null == serverEntity) {
            throw new ErrorCodeException(ErrorCode.of("outline.server.id.invalid", serverId));
        }

        return createOutlineAccessKey(serverEntity, name);
    }

    public OutlineAccessKeyEntity createOutlineAccessKey(OutlineServerEntity serverEntity, String name) {
        OutlineServerApi outlineServerApi = new OutlineServerApi(serverEntity.getApiUrl());
        AccessKey accessKey = outlineServerApi.createAccessKey();
        OutlineAccessKeyEntity entity = convert(serverEntity.getId(), accessKey);
        if (StringUtils.isNotBlank(name)) {
            entity.setName(name);
        }

        outlineAccessKeyService.save(entity);

        return entity;
    }

    public OutlineServer selectOutlineServer(Order order) {
        List<OutlineServerEntity> serverEntities = outlineServerRepository.findAll();
        List<OutlineServer> outlineServers = serverEntities.stream().map(OutlineServer::of).collect(Collectors.toList());
        OutlineServer selected = selectAgency(outlineServers, order);
        if (null != selected) {
            return selected;
        }

        return selectDefault(outlineServers, order);
    }

    private boolean matchOutlineServerType(OutlineServer outlineServer, Order order) {
        String fromOrder = order.getProductAttributes().getString("outline-server-type");
        String fromServer = outlineServer.getAttributes().getString("outline-server-type");
        if (StringUtils.isNotBlank(fromOrder) && fromOrder.equals(fromServer)) {
            return true;
        }

        return false;
    }

    private OutlineServer selectAgency(List<OutlineServer> outlineServers, Order order) {
        for (OutlineServer server : outlineServers) {
            if (matchOutlineServerType(server, order)) {
                Long userId = server.getAgencyUserId();
                if (null != userId && userId.equals(order.getBuyerId())) {
                    return server;
                }
            }
        }
        return null;
    }

    private OutlineServer selectDefault(List<OutlineServer> servers, Order order) {
        OutlineServer selected = null;
        int minAccessKeyCount = Integer.MAX_VALUE;
        for (OutlineServer server : servers) {
            if (matchOutlineServerType(server, order)) {
                Long userId = server.getAgencyUserId();
                if (null != userId && (server.getAccessKeyCount() < minAccessKeyCount)) {
                    minAccessKeyCount = server.getAccessKeyCount();
                    selected = server;
                }
            }
        }

        return selected;
    }

    @Override
    protected void onSaving(OutlineServerEntity entity, OutlineServerEntity originalEntity) {
        super.onSaving(entity, originalEntity);

        String originalName = null;
        String name = entity.getName();

        if (null == originalEntity) {
            ServerInfo serverInfo = syncOutlineServer(entity.getApiUrl());
            originalName = serverInfo.getName();

            entity.setId(serverInfo.getServerId());
            entity.setPort(serverInfo.getPortForNewAccessKeys());
            entity.setCreateTime((int) (serverInfo.getCreatedTimestampMs() / 1000));
            entity.setStatus(OutlineServerEntity.STATUS_RUNNING);
        } else {
            originalName = originalEntity.getName();
        }

        if (name.equals(originalName)) {
            OutlineServerApi outlineServerApi = new OutlineServerApi(entity.getApiUrl());
            ServerRenameParam serverRenameParam = new ServerRenameParam();
            serverRenameParam.setName(entity.getName());
            outlineServerApi.rename(serverRenameParam);
        }
    }

    public List<AccessKey> processOutlineServerAccessKeys(String serverId, OutlineServerApi outlineServerApi) {
        List<OutlineAccessKeyEntity> accessKeyEntities = outlineAccessKeyService.findByServerId(serverId);
        Map<String, OutlineAccessKeyEntity> entityMap = accessKeyEntities.stream().collect(Collectors.toMap(p->p.getAccessId(), p->p));

        List<AccessKey> accessKeys = outlineServerApi.listAccessKeys();

        List<OutlineAccessKeyEntity> insertEntities = new ArrayList<>();
        List<OutlineAccessKeyEntity> updateEntities = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(accessKeys)) {
            for (AccessKey accessKey : accessKeys) {
                OutlineAccessKeyEntity entity = entityMap.get(accessKey.getId());
                if (null == entity) {
                    insertEntities.add(convert(serverId, accessKey));
                }
            }

            Map<String, AccessKey> accessKeyMap = accessKeys.stream().collect(Collectors.toMap(p->p.getId(), p->p));
            for (OutlineAccessKeyEntity entity : accessKeyEntities) {
                if (accessKeyMap.get(entity.getAccessId()) == null) {
                    entity.setStatus(OutlineAccessKeyEntity.STATUS_DELETED);
                    updateEntities.add(entity);
                }
            }
        }

        for (OutlineAccessKeyEntity entity : insertEntities) {
            outlineAccessKeyService.save(entity);
        }
        for (OutlineAccessKeyEntity entity : updateEntities) {
            outlineAccessKeyService.update(entity);
        }

        return CollectionUtils.isNotEmpty(accessKeys) ? accessKeys : Collections.emptyList();
    }

    private OutlineAccessKeyEntity convert(String serverId, AccessKey accessKey) {
        OutlineAccessKeyEntity entity = new OutlineAccessKeyEntity();
        String id = serverId + "-" + accessKey.getId();
        entity.setId(id);
        entity.setOutlineServerId(serverId);
        entity.setAccessId(accessKey.getId());
        entity.setName(accessKey.getName());
        entity.setPassword(accessKey.getPassword());
        entity.setPort(accessKey.getPort());
        entity.setMethod(accessKey.getMethod());
        entity.setAccessUrl(accessKey.getAccessUrl());
        entity.setUsageBytes(0L);
        entity.setStatus(OutlineAccessKeyEntity.STATUS_UNUSED);

        return entity;
    }

}
