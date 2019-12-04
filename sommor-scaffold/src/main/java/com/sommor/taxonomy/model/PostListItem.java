package com.sommor.taxonomy.model;

import com.sommor.scaffold.utils.Converter;
import com.sommor.taxonomy.entity.PostEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Getter
@Setter
public class PostListItem {

    private Integer id;

    private Integer typeId;

    private String slug;

    private String title;

    private String subTitle;

    private Integer userId;

    private String cover;

    private String updateTime;

    private String createTime;

    public void fromEntity(PostEntity entity) {
        this.id = entity.getId();
        this.typeId = entity.getTypeId();
        this.slug = entity.getSlug();
        this.title = entity.getTitle();
        this.subTitle = entity.getSubTitle();
        this.userId = entity.getUserId();
        this.cover = entity.getCover();
        this.updateTime = Converter.convertDateTime(entity.getUpdateTime());
        this.createTime = Converter.convertDateTime(entity.getCreateTime());
    }
}
