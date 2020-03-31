package com.sommor.bundle.wechat.sdk;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class WechatApiResponse {

    private String errcode;

    private String errmsg;

    public boolean isSuccess() {
        return StringUtils.isBlank(errcode);
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
