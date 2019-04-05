package com.jlu.experiment.block.chain.common;

/**
 * description: 常量
 */
public class Constant {

    public interface ConfigKey{
        /**
         * 生成区块的时间间隔key
         */
        String BLOCK_TIME_RANGE = "blockTimeRange";

        /**
         * 交易限制阈值key
         */
        String TRANSACTION_LIMIT = "transactionLimit";
    }
}
