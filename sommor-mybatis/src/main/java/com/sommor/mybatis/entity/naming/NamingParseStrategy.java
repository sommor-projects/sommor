package com.sommor.mybatis.entity.naming;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */

@Extension(name = "对象字段名与数据库列名解析策略")
public interface NamingParseStrategy {

    NamingParseStrategy INST = ExtensionExecutor.proxyOf(NamingParseStrategy.class);

    String parseFieldName2ColumnName(String fieldName);

}
