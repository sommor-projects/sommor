package com.sommor.bundles.user.settings;

import com.sommor.core.scaffold.config.SettingOption;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/27
 */
public class UserSettings {

    @SettingOption(desc = "用户登录有效期时长，单位：秒")
    public static volatile int userLoginExpireTime = 10 * 24 * 60 * 60;

}
