package com.sommor.bundle.user.model;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public enum UserStatus {
    /**
     * 正常状态
     */
    NORMAL(1);

    private int status;

    UserStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
