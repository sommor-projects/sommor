package com.sommor.model.config;

import com.sommor.model.Model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class TargetConfig {

    private Model model;

    private Annotation annotation;

    private Field field;

    private Map<String, String> fieldFillFromSourceModelMap = new HashMap<>();

    public TargetConfig() {
        this.model = Model.of(this);
    }

    public Model model() {
        return this.model;
    }

    public <T extends Annotation> T annotation() {
        return (T) this.annotation;
    }

    public void annotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public void field(Field field) {
        this.field = field;
    }

    public Field field() {
        return this.field;
    }

    public Map<String, String> fieldFillFromSourceModelMap() {
        return fieldFillFromSourceModelMap;
    }
}
