package com.sommor.bundles.wechat.sdk;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class WechatSession extends WechatApiResponse {

    private String openid;

    private String session_key;

    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
