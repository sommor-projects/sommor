package com.sommor.taxonomy.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Table("posts")
@Getter @Setter
public class PostEntity extends SubjectBasedEntity {

    @Column
    private String subject;

    @Column
    private String slug;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private Integer userId;

    @Column
    private String cover;

    @Column
    private String description;

    @Override
    public String subject() {
        return "post";
    }
}
