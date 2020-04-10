package com.sommor.core.curd;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.repository.CurdRepository;
import lombok.Getter;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/16
 */
public class BaseCurdService<Entity extends BaseEntity> {

    @Resource
    private CurdManager curdManager;

    private CurdRepository<Entity> curdRepository;

    private CurdService curdService;

    @Getter
    private Class<Entity> entityClass;

    public BaseCurdService() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.entityClass = classes[0];
    }

    public BaseCurdService(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    @SuppressWarnings("unchecked")
    protected CurdRepository<Entity> curdRepository() {
        if (null == curdRepository) {
            if (null == entityClass) {
                throw new ErrorCodeException(ErrorCode.of("curd.service.entity.unknown", this.getClass().getName()));
            }

            curdRepository = curdManager.getCurdRepository(entityClass);
            if (null == curdRepository) {
                throw new ErrorCodeException(ErrorCode.of("curd.service.repository.unknown", this.getClass().getName()));
            }
        }

        return curdRepository;
    }

    protected CurdService<Entity> curdService() {
        if (null == curdService) {
            if (null == entityClass) {
                throw new ErrorCodeException(ErrorCode.of("curd.service.entity.empty", this.getClass().getName()));
            }

            curdService = curdManager.getCurdService(entityClass);
            if (null == curdService) {
                throw new ErrorCodeException(ErrorCode.of("curd.service.entity.unknown", this.getClass().getName(), entityClass.getSimpleName()));
            }
        }

        return curdService;
    }
}
