package com.sommor.scaffold.fields.subject.select;

import com.sommor.core.view.SelectView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/26
 */
public class SubjectSelectView extends SelectView {

    @Getter
    @Setter
    private String subject;

    public SubjectSelectView() {
        super("subject-select");
    }
}
