package com.sommor.bundles.wechat.sdk;

import com.sommor.core.scaffold.config.SettingOption;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class WechatConfig {

    @SettingOption(desc = "微信小程序的appId")
    public static String appId = "";

    @SettingOption(desc = "微信小程序的appSecret")
    public static String appSecret = "";

}
