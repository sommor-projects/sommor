package com.sommor.bundles.mall.view;

import com.sommor.scaffold.fields.conditional.Conditional;
import com.sommor.scaffold.fields.keywords.KeywordsField;
import com.sommor.scaffold.fields.subject.select.SubjectSelectField;
import com.sommor.scaffold.param.EntitySearchParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class SpuQuery extends EntitySearchParam {

    @Getter
    @Setter
    @KeywordsField(fields = {"title"})
    private String keywords;

    @Getter
    @Setter
    @SubjectSelectField(title = "所属店铺", subject = "shop")
    @Conditional
    private Integer shopId;
}
