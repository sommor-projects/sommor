package com.sommor.bundles.media.component.file;

import com.sommor.core.component.form.field.FormFieldConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/28
 */
public class MediaFilesFieldConfig extends FormFieldConfig<MediaFilesView> {

    @Getter
    @Setter
    private String entityName;

    @Getter
    @Setter
    private Long entityId;

    @Getter
    @Setter
    private Integer maxCount;

    @Getter
    @Setter
    private String cover;

    @Getter
    @Setter
    private String coverFieldName;
}
