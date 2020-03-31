package com.sommor.bundle.wine.service;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.mall.product.model.ProductForm;
import com.sommor.bundle.mall.shop.model.ShopForm;
import com.sommor.bundle.media.component.file.MediaFile;
import com.sommor.bundle.media.component.file.MediaFiles;
import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationSelection;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.model.Term;
import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundle.taxonomy.service.TaxonomyService;
import com.sommor.bundle.wine.jiukacha.JiuKaChaService;
import com.sommor.bundle.wine.jiukacha.JiuKaChaWineResult;
import com.sommor.bundle.wine.model.TaxonomyResult;
import com.sommor.bundle.wine.model.WineCrawlResult;
import com.sommor.bundle.wine.model.WineSearchRequest;
import com.sommor.model.Model;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/6
 */
@Service
public class WineCrawlService {

    @Resource
    private JiuKaChaService jiuKaChaService;

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Resource
    private TaxonomyService taxonomyService;

    @Resource
    private WineRepository wineRepository;

    public WineCrawlResult crawl(WineSearchRequest request) {
        WineCrawlResult crawlResult = new WineCrawlResult();
        crawlResult.setProductForm(new ProductForm());
        crawlResult.setShopForm(new ShopForm());
        crawlResult.setSpuTaxonomyResults(new ArrayList<>());

        crawlFromJiuKaCha(request, crawlResult);

        return crawlResult;
    }

    private void crawlFromJiuKaCha(WineSearchRequest request, WineCrawlResult crawlResult) {
        List<JiuKaChaWineResult> wineResults = jiuKaChaService.search(request);

        String wineName = request.getWineName();
        for (JiuKaChaWineResult wineResult : wineResults) {
            String subTitle = wineResult.getWine().getSubTitle();
            if (StringUtils.isNotBlank(subTitle) && subTitle.equalsIgnoreCase(wineName)) {
                parseJiuKaChaResult(wineResult, crawlResult);

                break;
            }
        }
    }

    private void parseJiuKaChaResult(JiuKaChaWineResult wineResult, WineCrawlResult crawlResult) {
        Integer shopId = null;

        Term winery = wineResult.getWinery();
        if (null != winery && (StringUtils.isNotBlank(winery.getSubTitle()))) {
            ShopForm shopForm = crawlResult.getShopForm();
            ShopEntity shopEntity = wineRepository.findWinery(winery.getSubTitle());
            if (null != shopEntity) {
                Model.fillData(shopForm, shopEntity);
            }

            shopForm.setTitle(winery.getTitle());
            shopForm.setSubTitle(winery.getSubTitle());
        }

        Term wine = wineResult.getWine();
        if (null != wine) {
            ProductForm productForm = crawlResult.getProductForm();
            if (StringUtils.isNotBlank(wine.getSubTitle())) {
                ProductEntity productEntity = wineRepository.findWineProductByWineName(wine.getSubTitle());
                if (null != productEntity) {
                    Model.fillData(productForm, productEntity);
                }

                productForm.setTitle(wine.getTitle());
                productForm.setSubTitle(wine.getSubTitle());

                MediaFiles mediaFiles = productForm.getPictures();
                if (null == mediaFiles) {
                    mediaFiles = new MediaFiles();
                    productForm.setPictures(mediaFiles);
                }

                boolean matched = CollectionUtils.isNotEmpty(productForm.getPictures())
                        && productForm.getPictures().stream().anyMatch(p -> p.getUri().equals(wineResult.getPictureUrl()));
                if (! matched) {
                    MediaFile mediaFile = new MediaFile();
                    mediaFile.setUri(wineResult.getPictureUrl());
                    mediaFiles.add(mediaFile);
                }

                productForm.setShopId(shopId);
            }
        }

        if (CollectionUtils.isNotEmpty(wineResult.getTaxonomies())) {
            crawlResult.getSpuTaxonomyResults().addAll(wineResult.getTaxonomies());
        }
    }

    public ProductEntity saveCrawlResult(WineCrawlResult crawlResult) {
        ShopEntity shopEntity = saveShopForm(crawlResult.getShopForm());

        ProductForm productForm = crawlResult.getProductForm();
        if (null != shopEntity) {
            productForm.setShopId(shopEntity.getId());
        }

        return saveProductForm(productForm, crawlResult.getSpuTaxonomyResults());
    }

    private ShopEntity saveShopForm(ShopForm shopForm) {
        if (StringUtils.isNotBlank(shopForm.getSubTitle())) {
            TaxonomyEntity taxonomyEntity = wineRepository.getWineryTaxonomy();
            TaxonomyRelationSelection selection = new TaxonomyRelationSelection();
            selection.setTaxonomyId(taxonomyEntity.getId());
            shopForm.setTaxonomy(selection);

            return wineRepository.saveWineryForm(shopForm);
        }

        return null;
    }

    private ProductEntity saveProductForm(ProductForm productForm, List<TaxonomyResult> taxonomyResults) {
        ProductEntity productEntity = null;

        TaxonomyRelationSelection selection = parseSpuTaxonomySelection(taxonomyResults);

        if (StringUtils.isNotBlank(productForm.getSubTitle())) {
            productForm.setTaxonomy(selection);
            productEntity = wineRepository.saveWineForm(productForm);
        }

        return productEntity;
    }

    private TaxonomyRelationSelection parseSpuTaxonomySelection(List<TaxonomyResult> taxonomyResults) {
        TaxonomyEntity wineTaxonomyEntity = wineRepository.getWineProductTaxonomy();

        TaxonomyRelationSelection selection = new TaxonomyRelationSelection();
        selection.setTaxonomyId(wineTaxonomyEntity.getId());

        for (TaxonomyResult taxonomyResult : taxonomyResults) {
            TaxonomyEntity type = taxonomyRepository.findByName(taxonomyResult.getName());

            Set<Integer> selected = new HashSet<>();

            if (CollectionUtils.isNotEmpty(taxonomyResult.getTerms())) {
                Integer parentId = type.getId();
                List<Integer> paths = new ArrayList<>();
                for (Term term : taxonomyResult.getTerms()) {
                    TaxonomyEntity taxonomyEntity = taxonomyRepository.findBySubTitle(type.getId(), term.getSubTitle());
                    if (null == taxonomyEntity) {
                        taxonomyEntity = new TaxonomyEntity();
                        taxonomyEntity.setTitle(term.getTitle());
                        taxonomyEntity.setSubTitle(term.getSubTitle());
                        taxonomyEntity.setParentId(parentId);
                        taxonomyEntity.setTypeId(type.getId());
                        taxonomyEntity = taxonomyService.save(taxonomyEntity);
                    }
                    paths.add(taxonomyEntity.getId());
                    if ("wine-region".equalsIgnoreCase(type.getName())) {
                        parentId = taxonomyEntity.getId();
                    }
                }

                if ("wine-region".equalsIgnoreCase(type.getName())) {
                    selected.add(paths.get(paths.size()-1));
                } else {
                    selected.addAll(paths);
                }
            }

            selection.addRelation(type.getName(), selected);
        }

        return selection;
    }
}
