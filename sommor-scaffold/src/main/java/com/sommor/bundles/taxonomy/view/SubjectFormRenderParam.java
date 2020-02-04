package com.sommor.bundles.taxonomy.view;

import com.sommor.scaffold.param.EntityFormRenderParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class SubjectFormRenderParam extends EntityFormRenderParam {

    @Getter
    @Setter
    @NotBlank
    private String taxonomy;

}
