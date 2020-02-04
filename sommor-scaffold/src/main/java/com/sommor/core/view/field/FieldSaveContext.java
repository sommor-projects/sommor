package com.sommor.core.view.field;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.scaffold.context.Context;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class FieldSaveContext extends FieldContext {

    @Setter
    private BaseEntity entity;

    @Setter
    private BaseEntity original;

    public FieldSaveContext(Context ctx) {
        super(ctx);
    }

    public <E extends BaseEntity> E getEntity() {
        return (E) this.entity;
    }

    public <E extends BaseEntity> E getOriginal() {
        return (E) this.original;
    }
}
