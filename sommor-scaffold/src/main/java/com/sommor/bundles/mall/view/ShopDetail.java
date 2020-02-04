package com.sommor.bundles.mall.view;

import com.sommor.mybatis.sql.field.type.Location;
import com.sommor.core.view.field.EntityDetail;
import com.sommor.core.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/25
 */
@Getter
@Setter
public class ShopDetail extends EntityDetail {

    @TextField
    private Integer userId;

    @TextField
    private String title;

    @TextField
    private String logo;

    @TextField
    private String webSite;

    @TextField
    private String openHours;

    @TextField
    private String description;

    @TextField
    private Location location;

    @TextField
    private Integer divisionId;

    @TextField
    private Integer taxonomy;

    @TextField
    private String address;

}
