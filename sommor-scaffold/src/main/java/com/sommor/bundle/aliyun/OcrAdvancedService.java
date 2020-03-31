package com.sommor.bundle.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */
@Service
public class OcrAdvancedService {

    private static final String APP_CODE = "59b3b65d02684364b9896770b09f2962";

    public String identify(OcrAdvancedRequest request) {
        String host = "https://ocrapi-advanced.taobao.com";
        String path = "/ocrservice/advanced";
        String appCode = APP_CODE;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> query = new HashMap<>();
        String body = JSON.toJSONString(request);

        try {
            HttpResponse response = HttpUtils.doPost(host, path, headers, query, body);
            System.out.println(response.toString());
            JSONObject o = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            return o.getString("content");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
