package com.jlu.experiment.block.chain.common;

/**
 * description: 常量
 */
public class Constant {

    public interface ConfigKey {
        /**
         * 生成区块的时间间隔key
         */
        String BLOCK_TIME_RANGE = "blockTimeRange";

        /**
         * 交易限制阈值key
         */
        String TRANSACTION_LIMIT = "transactionLimit";

        /**
         * 限制状态
         */
        String LIMIT_STATUS = "limitStatus";

        /**
         * 矿工状态
         */
        String WORKER_STATUS = "workerStatus";

        /**
         * 矿工挖矿耗时
         */
        String WORKER_TIME_COST = "workerTimeCost";

        /**
         * 区块承载交易最大数量
         */
        String BLOCK_LENGTH = "blockLength";
    }

    public interface LimitStatus {
        String LIMIT = "true";
        String RELEASE = "false";
    }

    public interface WorkerStatus {
        String WORK = "work";
        String STOP = "stop";
    }
}
