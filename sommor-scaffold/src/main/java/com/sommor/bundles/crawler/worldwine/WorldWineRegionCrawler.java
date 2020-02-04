package com.sommor.bundles.crawler.worldwine;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundles.taxonomy.service.TaxonomyService;
import com.sommor.bundles.taxonomy.utils.SlugParser;
import com.sommor.bundles.crawler.Crawler;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
public class WorldWineRegionCrawler implements Crawler {

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

        Map<String, String> map = parseQueryString(url);
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
    public String getUrl() {
        if (StringUtils.isNotBlank(region)) {
            return REGION_URL + "/" + region;
        }
        return REGION_URL;
    }

    protected Site buildSite() {
        Site site = Site.me()
                .setRetryTimes(3)
                .setSleepTime(1000)
                .addHeader("Referer", "https://m.wine-world.com/area")
                .setUserAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36");
        return site;
    }

    private Site site;

    @Override
    public Site getSite() {
        return  this.site == null ? (this.site = buildSite()) : this.site;
    }

    protected static Map<String, String> parseQueryString(String url) {
        Map<String, String> map = new HashMap<>();

        int i = url.indexOf('?');
        if (i > 0) {
            String qs = url.substring(i+1);
            String[] a = qs.split("&");
            for (String s : a) {
                String[] aa = s.split("=");
                map.put(aa[0], aa[1]);
            }
        }

        return map;
    }

    public static void main(String[] args) {
        System.out.println(parseQueryString("https://m.wine-world.com/area/france/rhone-valley/rasteau?typeId=1&parentId=23"));
    }
}
