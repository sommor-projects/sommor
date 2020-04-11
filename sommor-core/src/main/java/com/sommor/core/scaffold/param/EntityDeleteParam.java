package com.sommor.core.scaffold.param;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
@Getter
@Setter
public class EntityDeleteParam {

    private String id;

    private List<String> ids;
}
