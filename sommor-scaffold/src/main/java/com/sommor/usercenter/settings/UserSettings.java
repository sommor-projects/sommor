package com.sommor.usercenter.settings;

import com.sommor.scaffold.config.SettingOption;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/27
 */
public class UserSettings {

    @SettingOption(desc = "用户登录有效期时长，单位：毫秒")
    public static volatile Long userLoginExpireTime = 10 * 24 * 60 * 60 * 1000L;

}
