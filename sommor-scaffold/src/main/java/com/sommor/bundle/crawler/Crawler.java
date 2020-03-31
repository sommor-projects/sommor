package com.sommor.bundle.crawler;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
public interface Crawler extends PageProcessor {

    Request getRequest();
}
