package com.sommor.core.model.config;

import java.lang.annotation.Annotation;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class TargetConfigDefinition {

    private TargetAnnotation targetAnnotation;

    private Annotation annotation;

    public TargetConfigDefinition(TargetAnnotation targetAnnotation, Annotation annotation) {
        this.targetAnnotation = targetAnnotation;
        this.annotation = annotation;
    }

    public TargetAnnotation getTargetAnnotation() {
        return targetAnnotation;
    }

    public <A extends Annotation> A getAnnotation() {
        return (A) annotation;
    }
}
