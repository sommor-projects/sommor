package com.sommor.bundles.mall.view;

import com.sommor.bundles.media.fields.file.MediaFilesField;
import com.sommor.core.view.field.EntityTable;
import com.sommor.scaffold.fields.subject.count.SubjectCountField;
import com.sommor.core.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class ShopTable extends EntityTable {

    @Getter
    @Setter
    @TextField(title = "名称")
    private String title;

    @Getter
    @Setter
    @MediaFilesField(title = "Logo")
    private String logo;

    @Getter
    @Setter
    @SubjectCountField(title = "商品数", subject = "spu", subjectGroupFieldName = "shopId")
    private Integer spuCount;
}
