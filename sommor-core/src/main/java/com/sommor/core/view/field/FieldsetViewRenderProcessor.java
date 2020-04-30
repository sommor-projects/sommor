package com.sommor.core.view.field;

import com.sommor.extensibility.config.Implement;
import com.sommor.core.view.View;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;
import com.sommor.core.view.model.ViewTree;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/28
 */
@Implement
public class FieldsetViewRenderProcessor implements ViewRenderProcessor<FieldsetConfig> {

    @Override
    public void processOnViewRender(FieldsetConfig fieldsetConfig, ViewRenderContext ctx) {
        FieldsetView fieldsetView = ctx.getView();
        ViewTree viewTree = fieldsetConfig.getViewTree();

        String fullName = fieldsetView.getName();
        String pathName = fieldsetConfig.getPathName();
        if (StringUtils.isNotBlank(pathName)) {
            fullName = pathName + (StringUtils.isNotBlank(fullName) ? ("." + fullName) : "");
        }
        fieldsetView.setFullName(fullName);

        fieldsetView.setFields(parseFormFields(viewTree));
    }

    private Map<String, Object> parseFormFields(ViewTree viewTree) {
        Map<String, Object> fields = new LinkedHashMap<>();
        for (View view : viewTree.getViews()) {
            if (view instanceof ViewTree) {
                fields.put(view.getName(), parseFormFields((ViewTree) view));
            } else if (view instanceof FieldsetView) {
                fields.put(view.getName(), ((FieldsetView) view).getFields());
            } else {
                fields.put(view.getName(), view);
            }
        }

        return fields;
    }
}
