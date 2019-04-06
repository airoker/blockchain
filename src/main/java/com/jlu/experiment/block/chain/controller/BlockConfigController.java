package com.jlu.experiment.block.chain.controller;

import com.jlu.experiment.block.chain.common.Constant;
import com.jlu.experiment.block.chain.model.JsonResult;
import com.jlu.experiment.block.chain.service.BlockConfigService;
import org.apache.commons.lang3.StringUtils;
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
    public JsonResult<String> transactionLimit() {
        Long transactionLimit = blockConfigService.queryTransactionLimit();
        return JsonResult.success(transactionLimit.toString());
    }

    @RequestMapping("/blockTimeRange")
    @ResponseBody
    public JsonResult<String> blockTimeRange() {
        Long blockTimeRange = blockConfigService.queryBlockTimeRange();
        return JsonResult.success(blockTimeRange.toString());
    }

    @RequestMapping("/workerStatus")
    @ResponseBody
    public JsonResult<String> workerStatus() {
        String workerStatus = blockConfigService.queryWorkerStatus();
        return JsonResult.success(workerStatus);
    }

    @RequestMapping("/update/transactionLimit")
    @ResponseBody
    public JsonResult<String> updateTransactionLimit(@RequestParam("limit") long limit) {
        blockConfigService.modifyTransactionLimit(limit);
        return JsonResult.success("success");
    }

    @RequestMapping("/update/blockTimeRange")
    @ResponseBody
    public JsonResult<String> updateBlockTimeRange(@RequestParam("range") long range) {
        blockConfigService.modifyBlockTimeRange(range);
        return JsonResult.success("success");
    }

    @RequestMapping("/update/workerStatus")
    @ResponseBody
    public JsonResult<String> updateWorkerStatus(@RequestParam("value")String value) {
        if(StringUtils.equals(value, Constant.WorkerStatus.WORK)){
            blockConfigService.modifyWorkerStatus(Constant.WorkerStatus.WORK);
            return JsonResult.success("启动成功!");
        }else if(StringUtils.equals(value, Constant.WorkerStatus.STOP)){
            blockConfigService.modifyWorkerStatus(Constant.WorkerStatus.STOP);
            return JsonResult.success("停止成功!");
        }else {
            return JsonResult.error("参数有误！");
        }
    }
}
