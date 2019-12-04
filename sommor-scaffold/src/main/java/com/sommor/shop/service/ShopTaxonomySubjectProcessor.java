package com.sommor.shop.service;

import com.sommor.extensibility.config.Implement;
import com.sommor.taxonomy.form.TaxonomySubjectDefinition;
import com.sommor.taxonomy.form.TaxonomySubjectProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/1
 */
@Implement
public class ShopTaxonomySubjectProcessor implements TaxonomySubjectProcessor {

    @Override
    public TaxonomySubjectDefinition getSubjectDefinition() {
        return new TaxonomySubjectDefinition("shop", "店铺");
    }
}
