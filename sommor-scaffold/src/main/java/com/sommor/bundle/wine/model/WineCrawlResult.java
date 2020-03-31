package com.sommor.bundle.wine.model;

import com.sommor.bundle.mall.product.model.ProductForm;
import com.sommor.bundle.mall.shop.model.ShopForm;
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
