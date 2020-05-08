package com.sommor.core.component.bytes;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/3
 */
public enum ByteUnit {

    B(0), KB(1), MB(2), GB(3), TB(4)
    ;

    @Getter
    private int level;

    ByteUnit(int level) {
        this.level = level;
    }
}
