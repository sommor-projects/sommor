package com.sommor.bundles.wine.model;

import com.sommor.bundles.taxonomy.model.Term;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */
@Getter
@Setter
public class TaxonomyResult {

    private String name;

    private String title;

    private List<Term> terms;

    public void addTerm(Term term) {
        if (null == terms) {
            terms = new ArrayList<>();
        }

        terms.add(term);
    }
}
