package com.sommor.bundles.qiniu.settings;

import com.sommor.core.scaffold.config.SettingOption;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@SettingOption(desc = "七牛云配置")
public class QiniuSettings {

    @SettingOption(desc = "accessKey")
    public static String accessKey = "eGK8QTfAHz0oi4jzDwGTKOfubDooJi9BFM3aSLTS";

    @SettingOption(desc = "secureKey")
    public static String secureKey = "4d7mWscMmJ_lYfXcwVz2oljwGMlecmtR-Q2IDQzj";

    @SettingOption(desc = "存储空间bucket")
    public static String bucket = "sommor";

    @SettingOption(desc = "外链访问域名")
    public static String urlDomain = "http://q1voquwrp.bkt.clouddn.com/";
}
