package com.jlu.experiment.block.chain.dao;

import com.jlu.experiment.block.chain.model.Transaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 */
@Repository
public interface TransactionDao {
    int insertOne(Transaction transaction);

    long selectCountByStatus(@Param("status") int status);

    List<Transaction> selectTransaction(@Param("limit")long length, @Param("status") int status);

    void updateTransaction(@Param("blockNo")String blockNo, @Param("status")int status, @Param("ids")List<Long> ids);
}
