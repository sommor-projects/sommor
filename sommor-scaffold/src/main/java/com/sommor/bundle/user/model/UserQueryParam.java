package com.sommor.bundle.user.model;

import com.sommor.component.keywords.KeywordsField;
import com.sommor.scaffold.param.EntityQueryParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
public class UserQueryParam extends EntityQueryParam {

    @Getter
    @Setter
    @KeywordsField(fields = {"userName", "nickName"})
    private String keywords;

}
