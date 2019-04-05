package com.jlu.experiment.block.chain.dao;

import com.jlu.experiment.block.chain.model.BlockConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * description: 配置dao
 */
@Repository
public interface BlockConfigDao {

    int updateConfigByKey(@Param("key") String key, @Param("value") String value);

    BlockConfig selectByKey(@Param("key") String key);
}
