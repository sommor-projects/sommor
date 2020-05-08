package com.sommor.core.component.status;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
@Getter
@Setter
public class StatusVO {

    private Integer code;

    private String title;

    public StatusVO(StatusEnum statusEnum) {
        this.code = statusEnum.getCode();
        this.title = statusEnum.getTitle();
    }

    @Override
    public String toString() {
        return title;
    }
}
