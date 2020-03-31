package com.sommor.bundle.wine.jiukacha;

import com.sommor.bundle.taxonomy.model.Term;
import com.sommor.bundle.wine.model.TaxonomyResult;
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
public class JiuKaChaWineResult {

    private Term wine;

    private List<TaxonomyResult> taxonomies;

    private Term winery;

    private String year;

    private String wineId;

    private String sign;

    private String signVar;

    private String alcohol;

    private String pictureUrl;

    private String thumbUrl;

    public void addTaxonomyResult(TaxonomyResult result) {
        if (null == taxonomies) {
            taxonomies = new ArrayList<>();
        }

        taxonomies.add(result);
    }
}
