package com.sommor.taxonomy.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.taxonomy.entity.AttributeEntity;
import com.sommor.taxonomy.model.AttributeForm;
import com.sommor.taxonomy.repository.AttributeRepository;
import com.sommor.taxonomy.utils.SlugParser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Service
public class AttributeService {

    @Resource
    private AttributeRepository attributeRepository;

    public void save(AttributeForm form) {
        AttributeEntity entity = new AttributeEntity();

        entity.setId(form.getId());
        entity.setName(form.getName());
        entity.setTitle(form.getTitle());
        entity.setSlug(SlugParser.parse(form.getName()));

        attributeRepository.save(entity);
    }

    public AttributeForm render(Integer id) {
        AttributeEntity entity = attributeRepository.findById(id);
        if (null == entity) {
            throw new ErrorCodeException(ErrorCode.of("attribute.render.id.invalid", id));
        }

        AttributeForm form = new AttributeForm();
        form.setId(entity.getId());
        form.setName(entity.getName());
        form.setTitle(entity.getTitle());

        return form;
    }
}
