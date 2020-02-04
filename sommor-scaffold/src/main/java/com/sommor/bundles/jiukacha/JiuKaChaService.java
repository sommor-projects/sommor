package com.sommor.bundles.jiukacha;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sommor.core.model.Term;
import com.sommor.bundles.aliyun.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */
@Service
public class JiuKaChaService {

    public List<WineResult> search(WineSearchRequest request) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> query = new HashMap<>();
        Map<String, String> body = new HashMap<>();

        headers.put("origin", "https://search.9kacha.com");
        headers.put("referer", "https://search.9kacha.com/");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");

        body.put("dtype", "1");
        body.put("jparams", "{\"wordStr\":\""+request.getWineTitle()+"\",\"size\":10,\"offset\":0,\"format_rd\":\"search-k-w\",\"is_correct\":1,\"is_alias\":1,\"grape_blend_flag\":0,\"rowSearch\":{\"buyable\":null}}");
        try {
            HttpResponse httpResponse = HttpUtils.doPost(
                    "https://search.9kacha.com",
                    "/dataApi/get_data.php?0.2429958091869242",
                    headers, query, body
            );

            String response = EntityUtils.toString(httpResponse.getEntity());

            JSONObject jsonObject = JSONObject.parseObject(response);
            JSONArray data = jsonObject.getJSONArray("jsonData");
            if (null != data && ! data.isEmpty()) {
                List<WineResult> results = new ArrayList<>();

                String lowerCaseWineTitle = request.getWineTitle().toLowerCase();
                for (int i=0; i<data.size(); i++) {
                    WineResult wineResult = parseWine(data.getJSONObject(i));
                    Term wine = wineResult.getWine();
                    if (null != wine && ((null != wine.getTitle() && wine.getTitle().toLowerCase().contains(lowerCaseWineTitle))
                                    || (null != wine.getSubTitle() && wine.getSubTitle().toLowerCase().contains(lowerCaseWineTitle)))) {
                        results.add(wineResult);
                    }
                }

                if (results.size() > 1 && StringUtils.isNotBlank(request.getYear())) {
                    for (WineResult wineResult : results) {
                        if (request.getYear().equalsIgnoreCase(wineResult.getYear())) {
                            return Lists.newArrayList(wineResult);
                        }
                    }
                }

                if (results.isEmpty()) {
                    return null;
                }

                return results;
            }
            return null;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private WineResult parseWine(JSONObject data) {
        WineResult result = new WineResult();

        Term wine = parseTaxonomyTerm(data.getJSONObject("name"));
        result.setWine(wine);

        TaxonomyResult wineRegion = new TaxonomyResult();
        wineRegion.setName("wine-region");
        wineRegion.setTitle("产区");
        if (null != addTaxonomyTerm(wineRegion, data.getJSONObject("country"))) {
            if (null != addTaxonomyTerm(wineRegion, data.getJSONObject("region"))) {
                if (null != addTaxonomyTerm(wineRegion, data.getJSONObject("sub_region"))) {
                    addTaxonomyTerm(wineRegion, data.getJSONObject("village_region"));
                }
            }
        }
        result.addTaxonomyResult(wineRegion);

        Term winery = parseTaxonomyTerm(data.getJSONObject("winery"));
        result.setWinery(winery);

        TaxonomyResult wineType = new TaxonomyResult();
        wineType.setName("wine-type");
        wineType.setTitle("类型");
        addTaxonomyTerm(wineType, data.getJSONObject("wine_type"));
        result.addTaxonomyResult(wineType);

        TaxonomyResult wineStyle = new TaxonomyResult();
        wineStyle.setName("wine-style");
        wineStyle.setTitle("品种");
        addTaxonomyTerms(wineStyle, data.getJSONArray("grape"));
        result.addTaxonomyResult(wineStyle);

        result.setYear(data.getString("year"));
        result.setAlcohol(data.getString("alcohol"));
        result.setPictureUrl(data.getString("bigPicUrl"));
        result.setThumbUrl(data.getString("pic_url"));

        return result;
    }

    private void addTaxonomyTerms(TaxonomyResult result, JSONArray jsonArray) {
        if (null != jsonArray) {
            for (int i=0; i<jsonArray.size(); i++) {
                addTaxonomyTerm(result, jsonArray.getJSONObject(i));
            }
        }
    }

    private Term addTaxonomyTerm(TaxonomyResult result, JSONObject jsonObject) {
        Term term = parseTaxonomyTerm(jsonObject);
        if (null != term) {
            result.addTerm(term);
        }
        return term;
    }

    private Term parseTaxonomyTerm(JSONObject jsonObject) {
        if (null != jsonObject) {
            String title = jsonObject.getString("name_ch");
            String subTitle = jsonObject.getString("name_en");

            if (StringUtils.isNotBlank(title) || StringUtils.isNotBlank(subTitle)) {
                Term term = new Term();
                term.setTitle(filterHtml(title));
                term.setSubTitle(filterHtml(subTitle));

                return term;
            }
        }

        return null;
    }

    private String filterHtml(String s) {
        s = s.trim();

        if (s.contains("<") && s.contains(">")) {
            int n = s.length();
            StringBuilder builder = new StringBuilder(n);
            boolean filter = false;
            for (int i=0; i<n; i++) {
                char c = s.charAt(i);
                if (c == '<') {
                    filter = true;
                } else if (c == '>') {
                    filter = false;
                } else {
                    if (! filter) {
                        builder.append(c);
                    }
                }
            }

            return builder.toString();
        }

        return s;
    }
}
