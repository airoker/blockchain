package com.jlu.experiment.block.chain.service;

import com.jlu.experiment.block.chain.common.Constant;
import com.jlu.experiment.block.chain.model.BlockConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: 限制状态service
 */
@Service
public class LimitStatusService {

    @Resource
    private BlockConfigService blockConfigService;

    @Resource
    private BlockService blockService;

    synchronized public boolean checkLimit() {
        if (isLimit()) {
            // 已经是限制状态
            return true;
        }
        long unProcessCount = blockService.queryCountOfUnProcess();
        if (unProcessCount == 0) {
            return false;
        }
        Long transactionLimit = blockConfigService.queryTransactionLimit();
        if (unProcessCount >= transactionLimit) {
            //限制
            blockConfigService.modifyLimitStatus(Constant.LimitStatus.LIMIT);
            return true;
        } else {
            return false;
        }
    }

    synchronized public boolean checkRelease() {
        if (!isLimit()) {
            // 已经是解除限制状态
            return true;
        }

        long unProcessCount = blockService.queryCountOfUnProcess();

        Long transactionLimit = blockConfigService.queryTransactionLimit();
        if (unProcessCount < transactionLimit) {
            //解除限制
            blockConfigService.modifyLimitStatus(Constant.LimitStatus.RELEASE);
            return true;
        } else {
            return false;
        }
    }

    public boolean isLimit() {
        BlockConfig blockConfig = blockConfigService.queryByKey(Constant.ConfigKey.LIMIT_STATUS);
        return StringUtils.equals(blockConfig.getValue(), Constant.LimitStatus.LIMIT);
    }
}
