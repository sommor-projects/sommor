package com.sommor.bundles.outline.model;

import com.sommor.bundles.outline.client.response.AccessKey;
import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.core.component.bytes.BytesConvert;
import com.sommor.core.component.status.StatusField;
import com.sommor.core.model.enricher.EntityReference;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
@Getter
@Setter
public class OutlineOrderAccessKeyTable {

    private String id;

    @EntityReference(entity = OutlineAccessKeyEntity.NAME, byField = "id")
    private OutlineAccessKey accessKey;

}
