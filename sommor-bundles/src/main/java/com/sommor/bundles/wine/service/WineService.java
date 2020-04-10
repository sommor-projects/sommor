package com.sommor.bundles.wine.service;

import com.sommor.bundles.baiduyun.BaiduIdentifiedWine;
import com.sommor.bundles.baiduyun.BaiduYunService;
import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.wine.model.WineDetailVO;
import com.sommor.bundles.wine.model.WineSearchRequest;
import com.sommor.bundles.wine.model.WineCrawlResult;
import com.sommor.core.model.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
@Service
public class WineService {

    @Resource
    private BaiduYunService baiduYunService;

    @Resource
    private WineCrawlService wineCrawlService;

    @Resource
    private WineRepository wineRepository;

    public WineDetailVO renderWineDetail(ProductEntity productEntity) {
        WineDetailVO wineDetailVO = new WineDetailVO();
        Model.fillData(wineDetailVO, productEntity);
        return wineDetailVO;
    }

    public WineDetailVO identify(byte[] imageBytes) {
        BaiduIdentifiedWine wine = baiduYunService.identifyWine(imageBytes);
        if (null != wine && StringUtils.isNotBlank(wine.getWineName().getSubTitle())) {
            WineSearchRequest request = WineSearchRequest.of(wine.getWineName().getSubTitle());
            return this.search(request);
        }

        return null;
    }

    public WineDetailVO search(WineSearchRequest request) {
        // 1. 先从SPU表中查找
        ProductEntity productEntity = this.searchFromProduct(request);

        // 2. 从爬虫中查找
        if (null == productEntity) {
            WineCrawlResult crawlResult = wineCrawlService.crawl(request);
            // 3. 保存爬虫结果
            productEntity = wineCrawlService.saveCrawlResult(crawlResult);
        }

        if (null != productEntity) {
            return renderWineDetail(productEntity);
        }

        return null;
    }

    private ProductEntity searchFromProduct(WineSearchRequest request) {
        if (StringUtils.isNotBlank(request.getWineName())) {
            ProductEntity productEntity = wineRepository.findWineProductByWineName(request.getWineName());
            if (null != productEntity) {
                return productEntity;
            }
        }

        return null;
    }
}
