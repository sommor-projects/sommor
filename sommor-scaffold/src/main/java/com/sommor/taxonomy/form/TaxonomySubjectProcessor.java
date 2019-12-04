package com.sommor.taxonomy.form;

import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/1
 */
@Extension(name = "分类关联的目标对象处理器")
public interface TaxonomySubjectProcessor {

    default TaxonomySubjectDefinition getSubjectDefinition() {
        return null;
    }
}
