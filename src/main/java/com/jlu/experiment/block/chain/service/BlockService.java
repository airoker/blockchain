package com.jlu.experiment.block.chain.service;

import com.jlu.experiment.block.chain.dao.TransactionDao;
import com.jlu.experiment.block.chain.model.Transaction;
import com.jlu.experiment.block.chain.model.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * TODO completion javadoc.
 *
 * @since 2019/4/5
 */
@Service
public class BlockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockService.class);

    @Resource
    private TransactionDao transactionDao;

    public String createOneTransaction() {
        Transaction transaction = new Transaction();
        transaction.setBlockNo("");
        transaction.setContext("");
        transaction.setStatus(TransactionStatus.INIT.getStatus());
        UUID uuid = UUID.randomUUID();
        String transactionNo = uuid.toString();
        transaction.setTransactionNo(transactionNo);
        int count = transactionDao.insertOne(transaction);
        LOGGER.info("createOneTransaction insert one {}", count);
        return transactionNo;
    }
}
