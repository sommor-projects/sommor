package com.sommor.bundles.taxonomy.view;

import com.sommor.scaffold.param.EntityFormRenderParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
@Getter
@Setter
public class SubjectFormRenderParam extends EntityFormRenderParam {

    private String type;

    private Integer typeId;

}
