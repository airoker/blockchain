package com.jlu.experiment.block.chain.controller;

import com.jlu.experiment.block.chain.service.BlockService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 交易操作入口
 *
 * @since 2019/4/5
 */
@Controller
@RequestMapping("/block/chain/block")
public class BlockController {

    @Resource
    private BlockService blockService;

    @RequestMapping("/transaction/create")
    public String createTransaction(){
        String transactionNo = blockService.createOneTransaction();
        return transactionNo;
    }
}
