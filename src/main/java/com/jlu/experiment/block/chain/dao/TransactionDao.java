package com.jlu.experiment.block.chain.dao;

import com.jlu.experiment.block.chain.model.Transaction;
import org.springframework.stereotype.Repository;

/**
 * description:
 */
@Repository
public interface TransactionDao {
    int insertOne(Transaction transaction);
}
