package com.sommor.bundle.crawler;

import com.sommor.api.response.ApiResponse;
import com.sommor.bundle.wine.worldwine.WorldWineRegionCrawler;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@RestController
@RequestMapping(value = "/api/crawler")
public class CrawlerController {

    @Resource
    private CrawlerService crawlerService;

    @ApiOperation(value = "红酒世界产区")
    @RequestMapping(value = "/world-wine-region", method = RequestMethod.GET)
    public ApiResponse deleteBatch(@RequestParam(required = false) String region) {
        WorldWineRegionCrawler crawler = new WorldWineRegionCrawler(region);
        crawlerService.crawl(crawler);
        return ApiResponse.success();
    }
}
