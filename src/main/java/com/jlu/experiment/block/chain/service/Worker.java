package com.jlu.experiment.block.chain.service;

import com.google.common.collect.Lists;
import com.jlu.experiment.block.chain.model.Block;
import com.jlu.experiment.block.chain.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * description:
 * <p>
 * Created by linge on 19/4/7.
 */
@Service
public class Worker {
    private Logger logger = LoggerFactory.getLogger(Worker.class);

    @Resource
    private BlockService blockService;

    @Transactional
    public void doWork(long length) {
        if (length == 0) {
            return;
        }
        List<Transaction> transactionList = blockService.queryTransaction(length);
        if(CollectionUtils.isEmpty(transactionList)){
            logger.info("不存在交易 不生成区块");
            System.out.println("不存在交易 不生成区块");
            return;
        }
        Block block = new Block();
        String blockNo = UUID.randomUUID().toString();
        block.setBlockNo(blockNo);
        blockService.save(blockNo);
        List<Long> ids = Lists.newArrayList();
        for (Transaction transaction : transactionList) {
            ids.add(transaction.getId());
        }
        blockService.consumeTransaction(blockNo, ids);
        logger.info("create block {} ,consume transaction count:{}", blockNo, ids.size());
    }
}
