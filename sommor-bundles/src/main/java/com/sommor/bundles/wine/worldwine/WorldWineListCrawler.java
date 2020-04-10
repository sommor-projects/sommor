package com.sommor.bundles.wine.worldwine;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sommor.bundles.crawler.AbstractCrawler;
import com.sommor.core.utils.UrlUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/6
 */
public class WorldWineListCrawler extends AbstractCrawler {

    @Override
    protected Site buildSite() {
        return super.buildSite()
                .addHeader("Referer", "https://m.wine-world.com/wine");
    }

    @Override
    public Request getRequest() {
        return buildRequest(1);
    }

    private Request buildRequest(int pageIndex) {
        Request request = new Request("https://m.wine-world.com/wine/HitWine?pageIndex=" + pageIndex);
        request.setMethod(HttpConstant.Method.POST);

        Map<String,Object> params = new HashMap<>();
        params.put("pageIndex", pageIndex);

        request.setRequestBody(HttpRequestBody.form(params, "utf-8"));

        return request;
    }

    @Override
    public void process(Page page) {
        JSONObject jsonObject = JSONObject.parseObject(page.getRawText());
        JSONArray rows = jsonObject.getJSONArray("rows");
        if (CollectionUtils.isNotEmpty(rows)) {
            for (int i=0; i<rows.size(); i++) {
                JSONObject wine = rows.getJSONObject(i);

                WorldWineListItemResult result = new WorldWineListItemResult();
                result.setTitle(wine.getString("cname"));

                String subTitle = wine.getString("fname");
                if (StringUtils.isNotBlank(subTitle)) {
                    String[] a = subTitle.split(",");
                    subTitle = a[0];
                }
                result.setSubTitle(subTitle);
                result.setCoverUrl(wine.getString("cover"));

                page.putField("WorldWineListItemResult", result);
            }

            Map<String, String> query = UrlUtil.parseQueryString(page.getRequest().getUrl());
            Integer pageIndex = Integer.valueOf(query.get("pageIndex"));
            page.addTargetRequest(buildRequest(pageIndex+1));
        }
    }
}
