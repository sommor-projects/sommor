package com.sommor.taxonomy.service;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.scaffold.param.EntityInfoParam;
import com.sommor.scaffold.service.CurdService;
import com.sommor.taxonomy.entity.PostEntity;
import com.sommor.taxonomy.form.PostForm;
import com.sommor.taxonomy.model.*;
import com.sommor.taxonomy.param.SubjectFormRenderParam;
import com.sommor.taxonomy.param.PostQueryParam;
import com.sommor.taxonomy.repository.PostRepository;
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
        EntityInfoParam,
        PostListItem,
        PostQueryParam> {

    @Resource
    private PostRepository postRepository;

    @Override
    protected CurdRepository<PostEntity> curdRepository() {
        return postRepository;
    }
}
