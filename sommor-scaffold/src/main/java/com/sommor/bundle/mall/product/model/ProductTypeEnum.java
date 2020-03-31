package com.sommor.bundle.mall.product.model;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/7
 */
public enum ProductTypeEnum {
    /**
     * 可售卖商品
     */
    SALABLE(1),
    /**
     * 标准商品
     */
    SPU(2),
    /**
     * 可售卖且是标准商品
     */
    SPU_SALABLE(3),
    /**
     * SPU报价商品
     */
    SPU_QUOTATION(4),
    ;

    private int type;

    ProductTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static boolean isSpu(Integer type) {
        return null != type && (type == SPU.getType() || type == SPU_SALABLE.getType());
    }
}
