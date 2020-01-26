package com.sommor.bundles.user.view;

import com.sommor.scaffold.fields.keywords.KeywordsField;
import com.sommor.scaffold.param.EntitySearchParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
public class UserQueryParam extends EntitySearchParam {

    @Getter
    @Setter
    @KeywordsField(fields = {"userName"})
    private String keywords;

}
