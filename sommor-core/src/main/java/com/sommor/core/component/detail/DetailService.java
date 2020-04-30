package com.sommor.core.component.detail;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.core.curd.BaseCurdService;
import com.sommor.core.model.Model;
import com.sommor.mybatis.entity.BaseEntity;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
public class DetailService<Entity extends BaseEntity<?>, Detail, DetailParam> extends BaseCurdService<Entity> {

    private Class<Detail> detailClass;

    public DetailService() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.detailClass = classes[1];
    }

    public DetailService(Class<Entity> entityClass, Class<Detail> detailClass) {
        super(entityClass);
        this.detailClass = detailClass;
    }

    @SuppressWarnings("unchecked")
    public Detail renderDetail(DetailParam detailParam) {
        Entity entity;

        entity = (Entity) curdService().queryFirst(detailParam);
        if (null == entity) {
            throw new ErrorCodeException(ErrorCode.of("entity.detail.absence", this.detailClass.getSimpleName()));
        }

        Detail detail;
        try {
            detail = this.detailClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        Model model = Model.of(detail);
        model.fill(Model.of(entity));

        return detail;
    }
}
