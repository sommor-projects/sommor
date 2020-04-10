package com.sommor.bundles.wine.model;

import com.sommor.bundles.mall.product.model.ProductForm;
import com.sommor.bundles.mall.shop.model.ShopForm;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/6
 */
@Getter
@Setter
public class WineCrawlResult {

    private ProductForm productForm;

    private ShopForm shopForm;

    private List<TaxonomyResult> spuTaxonomyResults;
}
