package com.sommor.bundles.taxonomy.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundles.taxonomy.view.PostTable;
import com.sommor.bundles.user.auth.AuthenticationHolder;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.scaffold.view.field.Form;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.scaffold.service.CurdService;
import com.sommor.bundles.taxonomy.entity.PostEntity;
import com.sommor.bundles.taxonomy.view.PostForm;
import com.sommor.bundles.taxonomy.view.SubjectFormRenderParam;
import com.sommor.bundles.taxonomy.view.PostQueryParam;
import com.sommor.bundles.taxonomy.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
@Service
public class PostService extends CurdService<
        PostEntity,
        PostForm,
        SubjectFormRenderParam,
        PostEntity,
        EntityDetailParam,
        PostTable,
        PostQueryParam> {

    @Resource
    private PostRepository postRepository;

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    protected CurdRepository<PostEntity> curdRepository() {
        return postRepository;
    }

    @Override
    protected void onFormSaving(Form form, PostEntity entity, PostEntity originalEntity) {
        super.onFormSaving(form, entity, originalEntity);
        if (null == entity.getUserId()) {
            entity.setUserId(AuthenticationHolder.getAuthUser().getUserId());
        }
    }
}
