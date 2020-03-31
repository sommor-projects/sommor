package com.sommor.component.table;

import com.sommor.view.field.text.TextField;
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
    private Integer id;

}
