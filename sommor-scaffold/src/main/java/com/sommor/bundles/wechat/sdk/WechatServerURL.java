package com.sommor.bundles.wechat.sdk;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class WechatServerURL {

    private static final String BASE_URL = "https://api.weixin.qq.com/";

    private String apiName;

    private Parameters parameters;

    public WechatServerURL(String apiName) {
        this.apiName = apiName;

        this.parameters = new Parameters()
            .add("appid", WechatConfig.appId)
            .add("secret", WechatConfig.appSecret);
    }

    public WechatServerURL addParameter(String key, String value) {
        this.parameters.add(key, value);
        return this;
    }

    @Override
    public String toString() {
        return BASE_URL + apiName + "?" + this.parameters.toQueryString();
    }
}
