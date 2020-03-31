package com.sommor.component.currency;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
public enum CurrencyEnum {
    CNY(2, "¥", "人民币")
    ;

    @Getter
    private int fractionDigits;

    @Getter
    private String symbol;

    @Getter
    private String title;

    CurrencyEnum(int fractionDigits, String symbol, String title) {
        this.fractionDigits = fractionDigits;
        this.symbol = symbol;
        this.title = title;
    }

    public String getName() {
        return name();
    }

    public static CurrencyEnum of(Object currency) {
        if (currency instanceof CurrencyEnum) {
            return (CurrencyEnum) currency;
        } else if (currency instanceof String) {
            return valueOf((String) currency);
        }

        return null;
    }


    @Override
    public String toString() {
        return name();
    }
}
