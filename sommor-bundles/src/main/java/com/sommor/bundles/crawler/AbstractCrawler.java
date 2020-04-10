package com.sommor.bundles.crawler;

import us.codecraft.webmagic.Site;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
abstract public class AbstractCrawler implements Crawler {

    private Site site;

    protected Site buildSite() {
        Site site = Site.me()
                .setRetryTimes(3)
                .setSleepTime(1000)
                .setUserAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36");
        return site;
    }

    @Override
    public Site getSite() {
        return  this.site == null ? (this.site = buildSite()) : this.site;
    }
}
