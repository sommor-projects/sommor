package com.sommor.admin.ext.def;

import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Extension(name = "后台管理菜单")
public interface AdminMenuRender {

    String getAdminMenu();

}
