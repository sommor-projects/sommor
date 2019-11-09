package com.sommor.wechat.sdk;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class WechatAccessToken extends WechatApiResponse {

    private String accessToken;

    private Integer expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
