package com.sommor.scaffold.view.field;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.context.RequestContext;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class Fieldset {

    protected DataSource targetData;

    protected static final ExtensionExecutor<FieldProcessor> fieldProcessor = ExtensionExecutor.of(FieldProcessor.class);
    protected static final ExtensionExecutor<FieldInterceptor> fieldInterceptor = ExtensionExecutor.of(FieldInterceptor.class);

    public Fieldset(Object target) {
        this.targetData = new DataSource(target);
    }

    public Class getTargetClass() {
        return this.targetData.getTarget().getClass();
    }

    public DataSource getTargetData() {
        return this.targetData;
    }

    public void fill(DataSource sourceData) {
        fillData(this.targetData, sourceData);
    }

    public void fill(Object source) {
        DataSource sourceData = new DataSource(source);
        this.fill(sourceData);
    }

    public void query(Query query) {
        Object target = this.targetData.getTarget();
        if (target instanceof OnQuery) {
            ((OnQuery) target).onQuery(query);
        }

        for (String fieldName: targetData.getFieldNames()) {
            FieldDefinition fd = targetData.getField(fieldName);
            FieldQueryContext ctx = new FieldQueryContext(RequestContext.get().getSubContext(fd));
            ctx.setData(this.targetData);
            ctx.setDefinition(fd);
            ctx.setQuery(query);

            if (null != fd.getFieldConfig()) {
                fieldProcessor.run(
                        fd.getFieldConfig(),
                        ext -> ext.processOnQuery(fd.getFieldConfig(), ctx)
                );
            }

            fieldInterceptor.run(ext -> ext.interceptOnQuery(ctx));
        }
    }

    public Map<String, Object> toData() {
        return this.targetData.toData();
    }

    public static void fillData(Object target, Object source) {
        fillData(new DataSource(target), new DataSource(source));
    }

    public static void fillData(DataSource targetData, DataSource sourceData) {
        for (String fieldName: targetData.getFieldNames()) {
            FieldDefinition fd = targetData.getField(fieldName);

            Object value = sourceData.get(fieldName);
            if (! checkValueType(fd.getField().getType(), value)) {
                if (fd.getFieldConfig() != null) {
                    FieldContext ctx = new FieldContext(RequestContext.getSubContext(fd));
                    ctx.setDefinition(fd);
                    ctx.setData(sourceData);
                    ctx.setDefinition(fd);

                    if (null != fd.getFieldConfig()) {
                        value = fieldProcessor.executeFirstNotNull(
                                fd.getFieldConfig(),
                                ext -> ext.processOnFill(fd.getFieldConfig(), ctx)
                        );
                    }

                    if (null == value) {
                        value = fieldInterceptor.executeFirstNotNull(ext -> ext.interceptOnFill(ctx));
                    }
                } else {
                    value = null;
                }
            }

            targetData.set(fieldName, value);
        }

        Object target = targetData.getTarget();
        if (target instanceof OnFill) {
            ((OnFill) target).onFill(sourceData);
        }
    }

    protected static boolean checkValueType(Class type, Object value) {
        return value != null && type.isAssignableFrom(value.getClass());
    }
}
