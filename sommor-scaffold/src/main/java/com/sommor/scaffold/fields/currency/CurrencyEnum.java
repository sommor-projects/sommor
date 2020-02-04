package com.sommor.scaffold.fields.currency;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
public enum CurrencyEnum {
    CNY(100, "¥", "人民币")
    ;

    @Getter
    private int factor;

    @Getter
    private String symbol;

    @Getter
    private String title;

    CurrencyEnum(int factor, String symbol, String title) {
        this.factor = factor;
        this.symbol = symbol;
        this.title = title;
    }

    public String getName() {
        return name();
    }
}
