package com.sommor.core.component.currency;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wuyu
 * @since 2019/2/8
 */
public class Money implements Comparable<Money>, Serializable {

    private final CurrencyEnum currency;
    private final long cent;

    private static final long serialVersionUID = 1L;

    /**
     * just for json 反序列化，不对外暴露，不破坏原封装
     */
    private Money(){
        this.currency = CurrencyEnum.CNY;
        this.cent = 0L;
    }

    Money(long cent, CurrencyEnum currency) {
        this.currency = currency;
        this.cent = cent;
    }

    Money(long cent) {
        this(cent, CurrencyEnum.CNY);
    }

    public BigDecimal getAmount() {
        int fractionDigits = Math.max(0, currency.getFractionDigits());
        return BigDecimal.valueOf(cent, fractionDigits);
    }

    public long getCent() {
        return cent;
    }

    public Money add(Money other) {
        checkArgument(other.currency.equals(currency));
        return new Money(cent + other.cent, currency);
    }

    public Money subtract(Money other) {
        checkArgument(other.currency.equals(currency));
        return new Money(cent - other.cent, currency);
    }

    public Money multiply(long val) {
        return new Money(cent * val, currency);
    }

    public Money multiply(double val) {
        return new Money(Math.round(cent * val), currency);
    }

    public Money divide(double val) {
        checkArgument(val != 0);
        return new Money(Math.round(cent / val), currency);
    }

    public String format() {
        return currency.getSymbol() + getAmount();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Money other = (Money) obj;
        return this.cent == other.cent && this.currency.equals(other.currency);
    }

    @Override
    public int hashCode() {
        int h = 4;
        h = h * 31 + currency.hashCode();
        h = h * 31 + (int) (cent ^ (cent >>> 32));
        return h;
    }

    @Override
    public String toString() {
        // Obscure the string so that clients won't try to rely on this implementation.
        return "######## " + format();
    }

    @Override
    public int compareTo(Money o) {
        if (o == null) {
            return 1;
        }

        if (!o.currency.equals(currency)) {
            throw new IllegalArgumentException("can't compare money of different currencies");
        }

        return (int) Math.signum(cent - o.cent);
    }

    public CurrencyEnum getCurrency() {
        return this.currency;
    }

    public static Money fromMiniUnit(long cent) {
        return new Money(cent);
    }

    public static Money fromMiniUnit(long cent, CurrencyEnum currency) {
        return new Money(cent, currency);
    }

    public static Money fromCommonUnit(double yuan) {
        CurrencyEnum defaultCurrency = CurrencyEnum.CNY;
        return fromCommonUnit(yuan, defaultCurrency);
    }

    public static Money fromCommonUnit(double yuan, CurrencyEnum currency) {
        int fractionDigits = Math.max(0, currency.getFractionDigits());
        long cent = Math.round(yuan * Math.pow(10, fractionDigits));
        return new Money(cent);
    }

    private static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    private static void checkArgument(boolean expr) {
        if (!expr) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isPositive() {
        return cent > 0;
    }

    /**
     * @see #getAmount()
     */
    public String getAmountString() {
        return getAmount().toString();
    }
}

