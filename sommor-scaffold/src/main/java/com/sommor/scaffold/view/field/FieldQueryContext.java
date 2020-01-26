package com.sommor.scaffold.view.field;

import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.context.Context;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class FieldQueryContext extends FieldContext {

    @Getter
    @Setter
    private Query query;

    public FieldQueryContext() {
    }

    public FieldQueryContext(Context ctx) {
        super(ctx);
    }
}
