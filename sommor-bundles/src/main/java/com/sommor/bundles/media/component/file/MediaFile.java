package com.sommor.bundles.media.component.file;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
public class MediaFile {

    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String mediaSubjectRelationId;

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

    @Getter
    @Setter
    private String subjectGroup;

    @Setter
    @Getter
    private Integer priority;

}
