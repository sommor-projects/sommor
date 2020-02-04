package com.sommor.bundles.crawler;

import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@Service
public class CrawlerService {

    public void crawl(Crawler crawler) {
        Spider.create(crawler)
                .addUrl(crawler.getUrl())
                .thread(5)
                .run();
    }
}
