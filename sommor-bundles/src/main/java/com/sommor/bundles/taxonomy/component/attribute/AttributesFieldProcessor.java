package com.sommor.bundles.taxonomy.component.attribute;

import com.alibaba.fastjson.JSONObject;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.extensibility.config.Implement;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Implement
public class AttributesFieldProcessor implements FieldFillProcessor<AttributesFieldConfig> {

    @Override
    public Object processOnFieldFill(AttributesFieldConfig config, FieldFillContext ctx) {
        if (StringUtils.isNotBlank(config.getAttributes())) {
            Map<String, Object> map = JSONObject.parseObject(config.getAttributes());
            return new Attributes(map);
        }

        return new Attributes();
    }

}
