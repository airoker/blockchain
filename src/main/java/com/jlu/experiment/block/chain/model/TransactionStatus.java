package com.jlu.experiment.block.chain.model;

/**
 * description: 交易状态
 */
public enum TransactionStatus {
    INIT(0, "未处理"),
    SUCCESS(1, "已处理"),
    FAIL(2, "交易失败");

    private int status;
    private String desc;

    TransactionStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}
