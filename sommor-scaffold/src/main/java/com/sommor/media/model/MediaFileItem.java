package com.sommor.media.model;

import com.sommor.mybatis.entity.config.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
public class MediaFileItem {

    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private Integer mediaSubjectRelationId;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String uri;

    @Setter
    @Getter
    private String url;

    @Setter
    @Getter
    private String thumbUrl;

    @Setter
    @Getter
    private String mimeType;

    @Getter
    @Setter
    private Integer fileSize;

    @Setter
    @Getter
    private Integer priority;

}
