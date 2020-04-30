package com.sommor.bundles.wine.worldwine;

import com.sommor.bundles.crawler.AbstractCrawler;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundles.taxonomy.service.TaxonomyService;
import com.sommor.bundles.taxonomy.utils.SlugParser;
import com.sommor.core.utils.UrlUtil;
import com.sommor.core.spring.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
public class WorldWineRegionCrawler extends AbstractCrawler {

    private static final String REGION_URL = "https://m.wine-world.com/area";

    private static final String TAXONOMY_REGION_NAME = "wine-region";

    private String region;


    private TaxonomyService taxonomyService = ApplicationContextHolder.getBean(TaxonomyService.class);
    private TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);

    private TaxonomyEntity type;

    public WorldWineRegionCrawler() {
        this(null);
    }

    public WorldWineRegionCrawler(String region) {
        this.region = region;
        this.type = taxonomyRepository.findByKey(TAXONOMY_REGION_NAME);
    }

    @Override
    public void process(Page page) {
        String url = page.getRequest().getUrl();
        if (url.equalsIgnoreCase(REGION_URL)) {
            List<String> subRegionUrls = page.getHtml().$("ul.wine-r-list li").links().all();
            page.addTargetRequests(subRegionUrls);
            return;
        }

        String regionTitle = page.getHtml().$("div.reg-tit", "text").get();
        String regionSubTitle = page.getHtml().$("span.tit-en", "text").get();

        if (StringUtils.isBlank(regionTitle) || StringUtils.isBlank(regionSubTitle)) {
            System.out.println("region is null, url: " + url);
            return;
        }

        regionSubTitle = regionSubTitle.trim();
        String name = SlugParser.parse(regionSubTitle);
        TaxonomyEntity entity = taxonomyRepository.findByName(name, "wine-region");
        if (null == entity) {
            entity = new TaxonomyEntity();
        }

        if (null != regionTitle) {
            regionTitle = regionTitle.trim();
        }

        entity.setTitle(regionTitle);
        entity.setSubTitle(regionSubTitle);
        entity.setType(type.getName());
        entity.setName(name);

        Map<String, String> map = UrlUtil.parseQueryString(url);
        String parent;
        if (null == map.get("parent")) {
            parent = type.getName();
        } else {
            parent = map.get("parent");
        }
        entity.setParent(parent);

        taxonomyService.save(entity);

        String nextParent = entity.getName();

        System.out.println("=================================");
        System.out.println(url);
        System.out.println(regionTitle);
        System.out.println(regionSubTitle);
        System.out.println("=================================\n");

        List<String> links = page.getHtml().$("ul.reglist li").links().all();
        links = links.stream().map(s -> s + "?parent=" + nextParent).collect(Collectors.toList());
        page.addTargetRequests(links);
    }

    @Override
    public Request getRequest() {
        String url = REGION_URL;
        if (StringUtils.isNotBlank(region)) {
            url =  url + "/" + region;
        }

        return new Request(url);
    }

    @Override
    protected Site buildSite() {
        return super.buildSite()
                .addHeader("Referer", "https://m.wine-world.com/area");
    }
}
