package com.sommor.taxonomy.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.extensibility.config.Implement;
import com.sommor.scaffold.param.EntityInfoParam;
import com.sommor.scaffold.service.EntityAnnotatedProcessor;
import com.sommor.taxonomy.entity.PostEntity;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.form.PostForm;
import com.sommor.taxonomy.model.PostListItem;
import com.sommor.taxonomy.param.SubjectFormRenderParam;
import com.sommor.taxonomy.param.PostQueryParam;
import com.sommor.taxonomy.repository.TaxonomyRepository;
import com.sommor.usercenter.auth.AuthenticationHolder;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Implement
public class PostProcessor implements EntityAnnotatedProcessor<
        PostEntity,
        PostForm,
        SubjectFormRenderParam,
        PostEntity,
        EntityInfoParam,
        PostListItem,
        PostQueryParam> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    public void processOnInitQuery(PostQueryParam param) {
        TaxonomyEntity taxonomyEntity = parseTaxonomy(param.getTypeId(), param.getType());
        if (null == taxonomyEntity) {
            throw new ErrorCodeException(ErrorCode.of("post.query.type.invalid", param.getTypeId(), param.getType()));
        }

        param.setTypeId(taxonomyEntity.getId());
    }

    @Override
    public void processOnRenderListItem(PostEntity entity, PostListItem item) {
        item.fromEntity(entity);
    }

    private TaxonomyEntity parseTaxonomy(Integer typeId, String type) {
        if (null != typeId) {
            return taxonomyRepository.findById(typeId);
        } else if (null != type) {
            return taxonomyRepository.findByType(type);
        }

        return null;
    }

    @Override
    public void processOnFormRender(PostEntity entity, PostForm form, SubjectFormRenderParam param) {
    }

    @Override
    public void processOnFormSaving(PostEntity entity, PostEntity original, PostForm form) {
        entity.setUserId(AuthenticationHolder.getAuthUser().getUserId());
    }
}


