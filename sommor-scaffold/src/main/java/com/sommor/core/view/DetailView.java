package com.sommor.core.view;

import com.sommor.core.view.field.Detail;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/22
 */
public class DetailView extends FieldsetView {

    private Detail detail;

    public DetailView(Detail detail) {
        super("detail", detail.getTargetData());
        this.detail = detail;
    }

    public Map<String, Object> getFields() {
        return this.toFields();
    }

    public  Map<String, Object> getData() {
        return this.detail.toData();
    }

    public void render() {
        this.renderFieldsetView(this);
    }
}
