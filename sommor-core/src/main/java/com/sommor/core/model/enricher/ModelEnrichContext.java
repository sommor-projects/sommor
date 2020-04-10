package com.sommor.core.model.enricher;

import com.sommor.core.context.Context;
import com.sommor.core.model.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class ModelEnrichContext extends Context {

    @Getter
    @Setter
    private Model model;

    @Getter
    @Setter
    private List<Model> sourceModels;

    public ModelEnrichContext() {
    }

    public ModelEnrichContext(Context ctx) {
        super(ctx);
    }
}
