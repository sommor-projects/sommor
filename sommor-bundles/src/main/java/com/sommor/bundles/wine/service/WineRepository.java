package com.sommor.bundles.wine.service;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.mall.product.model.ProductForm;
import com.sommor.bundles.mall.product.model.ProductTypeEnum;
import com.sommor.bundles.mall.shop.model.ShopForm;
import com.sommor.bundles.mall.product.repository.ProductRepository;
import com.sommor.bundles.mall.shop.repository.ShopRepository;
import com.sommor.bundles.mall.product.service.ProductService;
import com.sommor.bundles.mall.shop.service.ShopFormService;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.mybatis.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/7
 */
@Service
public class WineRepository {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ProductService productService;

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Resource
    private ShopRepository shopRepository;

    @Resource
    private ShopFormService shopFormService;

    private TaxonomyEntity wineProductTaxonomy;

    private TaxonomyEntity wineryTaxonomy;

    public TaxonomyEntity getWineProductTaxonomy() {
        return wineProductTaxonomy == null ? wineProductTaxonomy = taxonomyRepository.findByKey("wines") : wineProductTaxonomy;
    }

    public TaxonomyEntity getWineryTaxonomy() {
        return wineryTaxonomy == null ? wineryTaxonomy = taxonomyRepository.findByKey("winery") : wineryTaxonomy;
    }

    public ProductEntity findWineProductByWineName(String wineName) {
        Query query = new Query()
                .where("taxonomyId", getWineProductTaxonomy().getId())
                .where("productType", ProductTypeEnum.SPU.getType())
                .where("subTitle", wineName);
        return productRepository.findFirst(query);
    }

    public ShopEntity findWinery(String wineryName) {
        Query query = new Query()
                .where("taxonomyId", getWineryTaxonomy().getId())
                .where("subTitle", wineryName);
        return shopRepository.findFirst(query);
    }

    public ProductEntity saveWineForm(ProductForm productForm) {
        productForm.setProductType(ProductTypeEnum.SPU.getType());
        //return productService.saveEntityForm(productForm);
        return null;
    }

    public ShopEntity saveWineryForm(ShopForm shopForm) {
        return shopFormService.saveForm(shopForm);
    }
}
