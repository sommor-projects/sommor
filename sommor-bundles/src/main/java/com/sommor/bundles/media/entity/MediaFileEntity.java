package com.sommor.bundles.media.entity;

import com.sommor.core.scaffold.entity.timed.TimedEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table("media_files")
@Getter
@Setter
public class MediaFileEntity extends TimedEntity<Long> {

    @Column
    private String uri;

    @Column
    private String mimeType;

    @Column
    private Integer fileSize;

}
