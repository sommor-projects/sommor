package com.sommor.bundle.taxonomy.model;

import com.sommor.component.datetime.DateTimeField;
import com.sommor.component.table.EntityTable;
import com.sommor.mybatis.sql.field.type.ConfigKey;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
public class PostTable extends EntityTable {

    @Getter
    @Setter
    private Integer typeId;

    @Getter
    @Setter
    private String slug;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String subTitle;

    @Getter
    @Setter
    private Integer userId;

    @Getter
    @Setter
    private String cover;

    @Getter
    @Setter
    @ConfigKey
    private String qrCode;

    @Getter
    @Setter
    @DateTimeField
    private String updateTime;

    @Getter
    @Setter
    @DateTimeField
    private String createTime;
}
