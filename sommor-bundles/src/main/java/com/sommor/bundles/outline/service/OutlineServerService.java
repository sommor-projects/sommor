package com.sommor.bundles.outline.service;

import com.sommor.bundles.mall.order.model.Order;
import com.sommor.bundles.outline.api.OutlineServer;
import com.sommor.bundles.outline.api.request.ServerRenameParam;
import com.sommor.bundles.outline.api.response.AccessKey;
import com.sommor.bundles.outline.api.response.ServerInfo;
import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.bundles.outline.model.OutlineAccessKeyCreateParam;
import com.sommor.bundles.outline.model.OutlineServerRenameParam;
import com.sommor.bundles.outline.model.OutlineServerSyncParam;
import com.sommor.bundles.outline.repository.OutlineAccessKeyRepository;
import com.sommor.bundles.outline.repository.OutlineServerRepository;
import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.curd.CurdService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private OutlineAccessKeyRepository outlineAccessKeyRepository;

    public OutlineServerEntity syncOutlineServer(OutlineServerSyncParam param) {
        OutlineServer outlineServer = new OutlineServer(param.getApiUrl());
        ServerInfo serverInfo = outlineServer.info();
        String serverId = serverInfo.getServerId();

        OutlineServerEntity entity = outlineServerRepository.findById(serverId);
        if (null == entity) {
            entity = new OutlineServerEntity();
            entity.setId(serverInfo.getServerId());
            entity.setName(serverInfo.getName());
            entity.setPort(serverInfo.getPortForNewAccessKeys());
            entity.setCreateTime((int) (serverInfo.getCreatedTimestampMs() / 1000));
            entity.setApiUrl(param.getApiUrl());
            entity.setAccessKeyCount(0);

            outlineServerRepository.add(entity);
        }

        processOutlineServerAccessKeys(serverId, outlineServer);

        return entity;
    }

    public List<OutlineAccessKeyEntity> findAccessKeys(String serverId) {
        return outlineAccessKeyRepository.findByServerId(serverId);
    }

    public OutlineServerEntity renameServer(OutlineServerRenameParam param) {
        OutlineServerEntity serverEntity = outlineServerRepository.findById(param.getServerId());
        if (null != serverEntity) {
            serverEntity.setName(param.getName());
            outlineServerRepository.update(serverEntity);
        }
        return serverEntity;
    }

    public OutlineAccessKeyEntity createOutlineAccessKey(String serverId) {
        OutlineServerEntity serverEntity = outlineServerRepository.findById(serverId);
        if (null == serverEntity) {
            throw new ErrorCodeException(ErrorCode.of("outline.server.id.invalid", serverId));
        }

        return createOutlineAccessKey(serverEntity);
    }

    public OutlineAccessKeyEntity createOutlineAccessKey(OutlineServerEntity serverEntity) {
        OutlineServer outlineServer = new OutlineServer(serverEntity.getApiUrl());
        AccessKey accessKey = outlineServer.createAccessKey();
        OutlineAccessKeyEntity entity = convert(serverEntity.getId(), accessKey);
        outlineAccessKeyRepository.add(entity);

        return entity;
    }

    public OutlineServerEntity selectOutlineServer(Order order) {
        List<OutlineServerEntity> serverEntities = outlineServerRepository.findAll();
        OutlineServerEntity selected = selectAgency(serverEntities, order);
        if (null != selected) {
            return selected;
        }

        return selectDefault(serverEntities, order);
    }

    private OutlineServerEntity selectAgency(List<OutlineServerEntity> serverEntities, Order order) {
        for (OutlineServerEntity entity : serverEntities) {
            Long userId = entity.getConfig().getLong("agencyUserId");
            if (null != userId && userId.equals(order.getUserId())) {
                return entity;
            }
        }
        return null;
    }

    private OutlineServerEntity selectDefault(List<OutlineServerEntity> serverEntities, Order order) {
        OutlineServerEntity selected = null;
        int minAccessKeyCount = Integer.MAX_VALUE;
        for (OutlineServerEntity entity : serverEntities) {
            Long userId = entity.getConfig().getLong("agencyUserId");
            if (null != userId && (entity.getAccessKeyCount() < minAccessKeyCount)) {
                minAccessKeyCount = entity.getAccessKeyCount();
                selected = entity;
            }
        }

        return selected;
    }

    @Override
    protected void onSaving(OutlineServerEntity entity, OutlineServerEntity originalEntity) {
        super.onSaving(entity, originalEntity);
        if (null != originalEntity && null != entity.getName() && originalEntity.getName().equals(entity.getName())) {
            OutlineServer outlineServer = new OutlineServer(entity.getApiUrl());
            ServerRenameParam serverRenameParam = new ServerRenameParam();
            serverRenameParam.setName(entity.getName());
            outlineServer.rename(serverRenameParam);
        }
    }

    public void processOutlineServerAccessKeys(String serverId, OutlineServer outlineServer) {
        List<OutlineAccessKeyEntity> accessKeyEntities = outlineAccessKeyRepository.findByServerId(serverId);
        Map<String, OutlineAccessKeyEntity> entityMap = accessKeyEntities.stream().collect(Collectors.toMap(p->p.getAccessId(), p->p));


        List<AccessKey> accessKeys = outlineServer.listAccessKeys();

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
            outlineAccessKeyRepository.add(entity);
        }
        for (OutlineAccessKeyEntity entity : updateEntities) {
            outlineAccessKeyRepository.update(entity);
        }
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
