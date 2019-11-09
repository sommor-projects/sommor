package com.sommor.press.ext;

import com.sommor.admin.ext.def.AdminMenuRender;
import com.sommor.extensibility.Priorities;
import com.sommor.extensibility.config.Implement;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Implement(priority = Priorities.HIGH)
public class PressAdminMenuRender implements InitializingBean, AdminMenuRender {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AdminMenuRender init...");
    }

    @Override
    public String getAdminMenu() {
        return "press menu";
    }
}
