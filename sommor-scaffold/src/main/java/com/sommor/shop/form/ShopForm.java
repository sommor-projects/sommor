package com.sommor.shop.form;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.sql.field.type.Config;
import com.sommor.mybatis.sql.field.type.Location;
import com.sommor.shop.entity.ShopEntity;
import com.sommor.taxonomy.form.TaxonomyBasedForm;
import com.sommor.taxonomy.form.TaxonomySelect;
import com.sommor.view.config.TextInput;
import com.sommor.view.form.EntityForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/3
 */
@Getter
@Setter
public class ShopForm extends TaxonomyBasedForm<ShopEntity> {

    @TextInput
    private Integer id;

    @TextInput
    private Integer userId;

    @TaxonomySelect(type = "division")
    private Integer countryId;

    @TextInput
    private String name;

    @TextInput
    private String webSite;

    @TextInput
    private String openHours;

    @TextInput
    private String description;

    private Location location;

    private String cover;

    @TextInput
    private String address;
}
