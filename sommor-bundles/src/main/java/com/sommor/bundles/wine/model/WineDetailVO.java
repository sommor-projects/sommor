package com.sommor.bundles.wine.model;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.component.shop.ShopDisplayField;
import com.sommor.bundles.mall.shop.model.ShopInfoVO;
import com.sommor.bundles.taxonomy.model.TaxonomySelectionVO;
import com.sommor.bundles.taxonomy.component.display.TaxonomyDisplayField;
import com.sommor.core.model.define.ModelAware;
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

    @TaxonomyDisplayField(entityName = ProductEntity.NAME, type = "wine-region", displayPaths = true)
    private TaxonomySelectionVO wineRegions;

    @TaxonomyDisplayField(entityName = ProductEntity.NAME, type = "wine-type")
    private TaxonomySelectionVO wineType;

    @TaxonomyDisplayField(entityName = ProductEntity.NAME, type = "wine-style")
    private TaxonomySelectionVO wineStyle;

    @ShopDisplayField
    private ShopInfoVO winery;
}
