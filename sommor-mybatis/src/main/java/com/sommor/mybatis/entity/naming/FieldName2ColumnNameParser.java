package com.sommor.mybatis.entity.naming;

import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Extension(name = "解析对象字段名到数据库列名")
public interface FieldName2ColumnNameParser {

    String parseFieldName2ColumnName(String fieldName);

}
