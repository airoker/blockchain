package com.jlu.experiment.block.chain.service;

import com.jlu.experiment.block.chain.dao.BlockDao;
import com.jlu.experiment.block.chain.dao.TransactionDao;
import com.jlu.experiment.block.chain.model.Block;
import com.jlu.experiment.block.chain.model.Transaction;
import com.jlu.experiment.block.chain.model.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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

    @Resource
    private BlockDao blockDao;

    public String createOneTransaction(Long value) {
        Transaction transaction = new Transaction();
        transaction.setBlockNo("");
        transaction.setContext(String.valueOf(value));
        transaction.setStatus(TransactionStatus.INIT.getStatus());
        UUID uuid = UUID.randomUUID();
        String transactionNo = uuid.toString();
        transaction.setTransactionNo(transactionNo);
        int count = transactionDao.insertOne(transaction);
        LOGGER.info("createOneTransaction insert one {}", count);
        return transactionNo;
    }

    public long queryCountOfUnProcess(){
        return transactionDao.selectCountByStatus(TransactionStatus.INIT.getStatus());
    }

    public long queryCountOfProcessed(){
        return transactionDao.selectCountByStatus(TransactionStatus.SUCCESS.getStatus());
    }

    public long queryBlockCount(){
        return blockDao.selectCount();
    }


    public List<Transaction> queryTransaction(long length) {
        return transactionDao.selectTransaction(length, TransactionStatus.INIT.getStatus());
    }

    public void save(String blockNo) {
        // 创建一个block
        blockDao.insertOne(blockNo);
    }

    public void consumeTransaction(String blockNo, List<Long> ids) {
        // 消费交易
        transactionDao.updateTransaction(blockNo, TransactionStatus.SUCCESS.getStatus(), ids);
    }
}
