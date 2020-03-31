package com.sommor.bundle.user.model;

import com.sommor.component.table.EntityTable;
import com.sommor.view.field.text.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class UserTable extends EntityTable {

    @Getter @Setter
    @TextField(title = "用户名")
    private String userName;

    @Getter @Setter
    @TextField(title = "手机号")
    private String mobilePhone;

    @Getter @Setter
    @TextField(title = "E-Mail")
    private String email;

    @Getter @Setter
    @TextField(title = "昵称")
    private String nickName;
}
