package com.sommor.bundle.wine.jiukacha;

import com.alibaba.fastjson.JSONObject;
import com.sommor.bundle.crawler.AbstractCrawler;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
public class JiuKaChaWineDetailCrawler extends AbstractCrawler {

    private String sign;

    private String signVar;

    private String wineId;

    private String year;

    public JiuKaChaWineDetailCrawler(String sign, String signVar, String wineId, String year) {
        this.sign = sign;
        this.signVar = signVar;
        this.wineId = wineId;
        this.year = year;
    }

    @Override
    public Request getRequest() {
        Request request = new Request("https://wine.9kacha.com/json80/recevice_json.php");
        request.setMethod(HttpConstant.Method.POST);

        Map<String,Object> params = new HashMap<>();
        params.put("jparams", "{\"command\":\"1017\",\"method\":\"\",\"client\":\"H5\",\"v\":\"1.0\",\"language\":\"ch\",\"signVar\":\""
                +signVar+"\",\"detail\":{\"wine_id\":\""+wineId+"\",\"year\":\""+year+"\",\"sign\":\""+sign+"\",\"source\":\"wordSearch\"}}");

        request.setRequestBody(HttpRequestBody.form(params, "utf-8"));

        return request;
    }

    @Override
    public void process(Page page) {
        String text = page.getRawText();
        JSONObject jsonObject = JSONObject.parseObject(text);

    }

    @Override
    protected Site buildSite() {
        return super.buildSite()
                .addHeader("referer", "https://h5.9kacha.com/index.php/Wine/detail?wine_id="+wineId+"&year="+year+"&sign="+sign+"&source=wordSearch&signVar="+signVar+"");
    }
}
