package com.sommor.bundles.baiduyun;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.ocr.AipOcr;
import com.sommor.core.model.Term;
import org.springframework.stereotype.Service;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */
@Service
public class BaiduYunService {

    private static AipOcr aipOcr = new AipOcr(
            "18376011", "ak1f8y6X1HX8d035lWwx2tnG", "ZG7otivrYyzoLFLVWD2Hf62UF7i0utYZ"
    );

    private static AipImageClassify client = new AipImageClassify(
            "18378149", "5oUTwgQvaEevikYsu73svtHm", "Rp72h7I6XK5OVzzkQ9nriGuGpmIqRnuc"
    );

    public JSONObject identity(byte[] image) {
        return new JSONObject(aipOcr.basicAccurateGeneral(image, null).toMap());
    }

    public BaiduIdentifiedWine identifyWine(byte[] image) {
        JSONObject response = new JSONObject(client.redwine(image, null).toMap());
        JSONObject result = response.getJSONObject("result");
        if (null != result) {
            BaiduIdentifiedWine wine = new BaiduIdentifiedWine();

            Term wineNameTerm = new Term();
            wineNameTerm.setTitle(result.getString("wineNameCn"));
            wineNameTerm.setSubTitle(result.getString("wineNameEn"));
            wine.setWineName(wineNameTerm);

            System.out.println(wineNameTerm);

            Term wineryTerm = new Term();
            wineryTerm.setTitle(result.getString("wineryCn"));
            wineryTerm.setSubTitle(result.getString("wineryEn"));
            wine.setWinery(wineryTerm);

            return wine;
        }

        return null;
    }
}
