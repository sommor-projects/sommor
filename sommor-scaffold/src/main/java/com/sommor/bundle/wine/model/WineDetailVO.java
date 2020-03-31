package com.sommor.bundle.wine.model;

import com.sommor.bundle.mall.product.entity.SpuEntity;
import com.sommor.bundle.mall.product.component.shop.ShopDisplayField;
import com.sommor.bundle.mall.shop.model.ShopInfoVO;
import com.sommor.bundle.taxonomy.model.TaxonomySelectionVO;
import com.sommor.bundle.taxonomy.component.display.TaxonomyDisplayField;
import com.sommor.model.define.ModelAware;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
@Getter
@Setter
public class WineDetailVO {

    @ModelAware
    private WineInfoVO wine;

    @TaxonomyDisplayField(entityName = SpuEntity.NAME, type = "wine-region", displayPaths = true)
    private TaxonomySelectionVO wineRegions;

    @TaxonomyDisplayField(entityName = SpuEntity.NAME, type = "wine-type")
    private TaxonomySelectionVO wineType;

    @TaxonomyDisplayField(entityName = SpuEntity.NAME, type = "wine-style")
    private TaxonomySelectionVO wineStyle;

    @ShopDisplayField
    private ShopInfoVO winery;
}
