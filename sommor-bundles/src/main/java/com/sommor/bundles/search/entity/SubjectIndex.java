package com.sommor.bundles.search.entity;

import com.sommor.bundles.search.component.attribute.AttributeIndexField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@Getter
@Setter
@Document(indexName = "subject", replicas = 0)
public class SubjectIndex {

    private Long id;

    private String entity;

    private Long userId;

    private String title;

    private String subTitle;

    @AttributeIndexField
    private AttributeIndex taxonomy;

    @AttributeIndexField
    private List<AttributeIndex> attributes;

}
