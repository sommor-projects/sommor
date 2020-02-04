package com.sommor.scaffold.fields.subject.select;

import com.sommor.scaffold.fields.keywords.KeywordsField;
import com.sommor.scaffold.param.EntityQueryParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/27
 */
public class SubjectSelectParam extends EntityQueryParam {

    @Getter
    @Setter
    @KeywordsField(fields = {"userName"})
    private String keywords;
}
