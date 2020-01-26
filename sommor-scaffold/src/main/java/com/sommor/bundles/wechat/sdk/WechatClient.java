package com.sommor.bundles.wechat.sdk;

import com.alibaba.fastjson.JSON;
import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author wuyu
 * @since 2019/2/5
 */
@Service
public class WechatClient {
    private OkHttpClient okHttpClient = new OkHttpClient();


    public WechatAccessToken accessToken() {
        String url = new WechatServerURL("cgi-bin/token")
            .addParameter("grant_type", "client_credential")
            .toString();

        return get(url, WechatAccessToken.class);
    }

    private <WxResponse extends WechatApiResponse> WxResponse get(String url, Class<WxResponse> responseClass) {
        Request request = new Request.Builder()
            .url(url)
            .get()
            .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (null == response.body()) {
                throw new ErrorCodeException(ErrorCode.of("weixinClient.get-responseBodyIsNull", url));
            }

            WxResponse wxResponse = JSON.parseObject(response.body().string(), responseClass);
            return wxResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WechatSession jscode2session(String code) {
        String url = new WechatServerURL("sns/jscode2session")
            .addParameter("js_code", code)
            .addParameter("grant_type", "authorization_code")
            .toString();

        return get(url, WechatSession.class);
    }

}
