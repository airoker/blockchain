package com.jlu.experiment.block.chain.service;

import com.jlu.experiment.block.chain.common.Constant;
import com.jlu.experiment.block.chain.dao.BlockConfigDao;
import com.jlu.experiment.block.chain.model.BlockConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: 配置服务
 */
@Service
public class BlockConfigService {

    @Resource
    private BlockConfigDao blockConfigDao;

    public int modifyBlockTimeRange(long blockRange) {
        return modifyNumConfig(Constant.ConfigKey.BLOCK_TIME_RANGE, blockRange);
    }

    public int modifyTransactionLimit(long limit) {
        return modifyNumConfig(Constant.ConfigKey.TRANSACTION_LIMIT, limit);
    }

    public Long queryBlockTimeRange() {
        String key = Constant.ConfigKey.BLOCK_TIME_RANGE;
        return queryNumValue(key);
    }

    public Long queryTransactionLimit() {
        String key = Constant.ConfigKey.TRANSACTION_LIMIT;
        return queryNumValue(key);
    }

    private Long queryNumValue(String key) {
        BlockConfig blockConfig = queryByKey(key);
        if (blockConfig != null) {
            return Long.valueOf(blockConfig.getValue());
        }
        return null;
    }

    private int modifyConfig(String key, String value) {
        return blockConfigDao.updateConfigByKey(key, value);
    }

    private int modifyNumConfig(String key, long value) {
        return modifyConfig(key, String.valueOf(value));
    }

    public BlockConfig queryByKey(String key) {
        return blockConfigDao.selectByKey(key);
    }


    public void modifyLimitStatus(String aTrue) {
        modifyConfig(Constant.ConfigKey.LIMIT_STATUS, aTrue);
    }

    public String queryWorkerStatus() {
        String key = Constant.ConfigKey.WORKER_STATUS;
        BlockConfig blockConfig = queryByKey(key);
        if (blockConfig != null) {
            return blockConfig.getValue();
        } else {
            return Constant.WorkerStatus.STOP;
        }
    }

    public void modifyWorkerStatus(String value) {
        modifyConfig(Constant.ConfigKey.WORKER_STATUS, value);
    }

    public Long queryWorkerTimeCost(){
        return queryNumValue(Constant.ConfigKey.WORKER_TIME_COST);
    }

    public Long queryBlockLength() {
        return queryNumValue(Constant.ConfigKey.BLOCK_LENGTH);
    }
}
