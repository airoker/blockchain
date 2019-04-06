package com.jlu.experiment.block.chain.controller;

import com.jlu.experiment.block.chain.common.Constant;
import com.jlu.experiment.block.chain.model.JsonResult;
import com.jlu.experiment.block.chain.model.WorkerStatusVo;
import com.jlu.experiment.block.chain.service.BlockService;
import com.jlu.experiment.block.chain.service.LimitStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 交易操作入口
 *
 * @since 2019/4/5
 */
@Controller
@RequestMapping("/block/chain/block")
public class BlockController {
    private Logger logger = LoggerFactory.getLogger(BlockController.class);

    @Resource
    private BlockService blockService;

    @Resource
    private LimitStatusService limitStatusService;

    @RequestMapping("/transaction/create")
    @ResponseBody
    public JsonResult<String> createTransaction(@RequestParam("value") Long value) {
        if (limitStatusService.isLimit()) {
            logger.info("交易受到限制，不能提交");
            return JsonResult.error("交易受到限制，不能提交!");
        }
        String transactionNo = blockService.createOneTransaction(value);
        limitStatusService.checkLimit();
        return JsonResult.success("提交成功，交易流水号：" + transactionNo);
    }

    @RequestMapping("/transaction/autoCreate")
    @ResponseBody
    public JsonResult<String> createTransaction(@RequestParam("value") Long value,
                                    @RequestParam("count") Long count,
                                    @RequestParam("interval") double interval) throws InterruptedException {
        if (limitStatusService.isLimit()) {
            logger.info("交易受到限制，不能提交");
            return JsonResult.error("交易受到限制，不能提交!");
        }
        double millSecond = interval * 1000;
        long waitTime = (long) millSecond;
        int successCount = 0;
        for (long i = 0; i < count; i++) {
            blockService.createOneTransaction(value);
            successCount++;
            boolean limit = limitStatusService.checkLimit();
            if (limit) {
                logger.info("达到限制状态，不再创建交易， count:{}", i);
                break;
            }
            if (waitTime > 0) {
                Thread.sleep(waitTime);
            }
        }
        return JsonResult.success("成功创建交易" + successCount + "个");
    }

    @RequestMapping("/transaction/limitStatus")
    @ResponseBody
    public JsonResult<String> createTransaction() {
        if (limitStatusService.isLimit()) {
            return JsonResult.success(Constant.LimitStatus.LIMIT);
        } else {
            return JsonResult.success(Constant.LimitStatus.RELEASE);
        }
    }

    @RequestMapping("/worker/countStatus")
    @ResponseBody
    public JsonResult<WorkerStatusVo> queryWorkerStatus(){
        WorkerStatusVo data = new WorkerStatusVo();
        long processed = blockService.queryCountOfProcessed();
        data.setProcessedCount(processed);
        long unprocess = blockService.queryCountOfUnProcess();
        data.setUnprocessCount(unprocess);
        long blockCount = blockService.queryBlockCount();
        data.setBlockCount(blockCount);
        return JsonResult.success(data);
    }
}
