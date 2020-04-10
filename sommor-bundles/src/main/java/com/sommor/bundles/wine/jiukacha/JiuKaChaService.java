package com.sommor.bundles.wine.jiukacha;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sommor.bundles.taxonomy.model.Term;
import com.sommor.bundles.aliyun.HttpUtils;
import com.sommor.bundles.wine.model.TaxonomyResult;
import com.sommor.bundles.wine.model.WineSearchRequest;
import com.sommor.core.utils.EscapeUtil;
import com.sommor.core.utils.UrlUtil;
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

    public List<JiuKaChaWineResult> search(WineSearchRequest request) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> query = new HashMap<>();
        Map<String, String> body = new HashMap<>();

        headers.put("origin", "https://search.9kacha.com");
        headers.put("referer", "https://search.9kacha.com/");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");

        body.put("dtype", "1");
        body.put("jparams", "{\"wordStr\":\""+request.getWineName()+"\",\"size\":10,\"offset\":0,\"format_rd\":\"search-k-w\",\"is_correct\":1,\"is_alias\":1,\"grape_blend_flag\":0,\"rowSearch\":{\"buyable\":null}}");
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
                List<JiuKaChaWineResult> results = new ArrayList<>();

                String lowerCaseWineTitle = request.getWineName().toLowerCase();
                for (int i=0; i<data.size(); i++) {
                    JiuKaChaWineResult wineResult = parseWine(data.getJSONObject(i));
                    Term wine = wineResult.getWine();
                    if (null != wine && ((null != wine.getTitle() && wine.getTitle().toLowerCase().contains(lowerCaseWineTitle))
                                    || (null != wine.getSubTitle() && wine.getSubTitle().toLowerCase().contains(lowerCaseWineTitle)))) {
                        results.add(wineResult);
                    }
                }

                if (results.size() > 1 && StringUtils.isNotBlank(request.getYear())) {
                    for (JiuKaChaWineResult wineResult : results) {
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

    private JiuKaChaWineResult parseWine(JSONObject data) {
        JiuKaChaWineResult result = new JiuKaChaWineResult();

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

        result.setWineId(data.getString("wine_id"));
        result.setSign(data.getString("sign"));

        String h5Url = data.getString("h5_url");
        Map<String, String> query = UrlUtil.parseQueryString(h5Url);
        result.setSignVar(query.get("signVar"));
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
                term.setTitle(EscapeUtil.escapeHtml(StringUtils.trim(title)));
                term.setSubTitle(alphanumeric(EscapeUtil.escapeHtml(StringUtils.trim(subTitle))));

                return term;
            }
        }

        return null;
    }

    private static String alphanumeric(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                builder.append(c);
            } else if (builder.charAt(builder.length()-1) != ' ') {
                builder.append(" ");
            }
        }
        System.out.println("alphanumeric: " + s + " -> " + builder.toString());
        return builder.toString();
    }
}
