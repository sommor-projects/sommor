package com.sommor.bundle.wine.worldwine;

import com.sommor.bundle.crawler.AbstractCrawler;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundle.taxonomy.service.TaxonomyService;
import com.sommor.bundle.taxonomy.utils.SlugParser;
import com.sommor.core.utils.UrlUtil;
import com.sommor.scaffold.spring.ApplicationContextHolder;
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
        this.type = taxonomyRepository.findByName(TAXONOMY_REGION_NAME);
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

        String name = TAXONOMY_REGION_NAME + "-" + SlugParser.parse(regionSubTitle);
        TaxonomyEntity entity = taxonomyRepository.findByName(name);
        if (null == entity) {
            entity = new TaxonomyEntity();
        }

        entity.setTitle(regionTitle);
        entity.setSubTitle(regionSubTitle);
        entity.setTypeId(type.getId());
        entity.setName(name);

        Map<String, String> map = UrlUtil.parseQueryString(url);
        Integer parentId;
        if (null == map.get("parentId")) {
            parentId = type.getId();
        } else {
            parentId = Integer.valueOf(map.get("parentId"));
        }
        entity.setParentId(parentId);

        taxonomyService.save(entity);

        Integer id = entity.getId();

        System.out.println("=================================");
        System.out.println(url);
        System.out.println(regionTitle);
        System.out.println(regionSubTitle);
        System.out.println("=================================\n");

        List<String> links = page.getHtml().$("ul.reglist li").links().all();
        links = links.stream().map(s -> s + "?parentId=" + id).collect(Collectors.toList());
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
