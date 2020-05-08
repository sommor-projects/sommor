package com.sommor.core.component.status;

import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.extensibility.config.Implement;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
@Implement
public class StatusFieldProcessor implements FieldFillProcessor<StatusFieldConfig> {

    @Override
    public Object processOnFieldFill(StatusFieldConfig config, FieldFillContext ctx) {
        String statusFieldName = config.getStatusFieldName();
        Integer status = config.getStatus();

        if (StringUtils.isBlank(statusFieldName)) {
            statusFieldName = ctx.getModelField().getName();
            status = ctx.getSourceModelFieldValue();
        }

        StatusEnum statusEnum = StatusManager.statusOf(statusFieldName, status);

        Class type = ctx.getModelField().getType();
        if (StatusEnum.class.isAssignableFrom(type)) {
            return statusEnum;
        } else if (type == StatusVO.class) {
            return new StatusVO(statusEnum);
        }

        return null;
    }
}
