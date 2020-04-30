package com.sommor.bundles.outline.model;

import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.bundles.taxonomy.component.attribute.Attributes;
import com.sommor.bundles.taxonomy.component.attribute.AttributesField;
import com.sommor.core.component.entity.count.EntityCountField;
import com.sommor.core.model.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Getter
@Setter
public class OutlineServer {

    private String id;

    private String name;

    private Long agencyUserId;

    private String apiUrl;

    @EntityCountField(foreignEntity = OutlineAccessKeyEntity.NAME, foreignIdName = "outlineServerId")
    private Integer accessKeyCount;

    private Integer startTime;

    private Integer endTime;

    private String taxonomy;

    @AttributesField
    private Attributes attributes;

    public static OutlineServer of(OutlineServerEntity entity) {
        OutlineServer outlineServer = new OutlineServer();
        Model.fillData(outlineServer, entity);
        return outlineServer;
    }
}
