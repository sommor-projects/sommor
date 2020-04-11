package com.sommor.core.component.table;

import com.sommor.core.view.field.text.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/25
 */
public class EntityTable {

    @Getter
    @Setter
    @TextField(title = "ID")
    private String id;

}
