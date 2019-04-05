package com.jlu.experiment.block.chain.controller;

import com.jlu.experiment.block.chain.service.BlockConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 配置入口
 *
 * @since 2019/4/5
 */
@Controller
@RequestMapping("/block/chain/config")
public class BlockConfigController {

    @Resource
    private BlockConfigService blockConfigService;

    @RequestMapping("/transactionLimit")
    @ResponseBody
    public String transactionLimit() {
        Long transactionLimit = blockConfigService.queryTransactionLimit();
        return transactionLimit.toString();
    }

    @RequestMapping("/blockTimeRange")
    @ResponseBody
    public String blockTimeRange() {
        Long blockTimeRange = blockConfigService.queryBlockTimeRange();
        return blockTimeRange.toString();
    }

    @RequestMapping("/update/transactionLimit")
    @ResponseBody
    public String updateTransactionLimit(@RequestParam("limit") long limit) {
        blockConfigService.modifyTransactionLimit(limit);
        return "success";
    }

    @RequestMapping("/update/blockTimeRange")
    @ResponseBody
    public String updateBlockTimeRange(@RequestParam("range") long range) {
        blockConfigService.modifyBlockTimeRange(range);
        return "success";
    }
}
