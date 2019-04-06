package com.jlu.experiment.block.chain.service;

import com.google.common.collect.Lists;
import com.jlu.experiment.block.chain.common.Constant;
import com.jlu.experiment.block.chain.model.Block;
import com.jlu.experiment.block.chain.model.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * description:
 * <p>
 * Created by linge on 19/4/7.
 */
@Service
public class WorkerService {
    private Logger logger = LoggerFactory.getLogger(WorkerService.class);

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Resource
    private BlockConfigService blockConfigService;

    @Resource
    private Worker worker;

    // 不停检测
    @PostConstruct
    public void init() {
        scheduledExecutorService.schedule(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                work();
                return null;
            }
        }, 3, TimeUnit.SECONDS);
    }

    public void work() throws InterruptedException {
        String status = blockConfigService.queryWorkerStatus();
        if (StringUtils.equals(Constant.WorkerStatus.WORK, status)) {
            logger.info("working, generate one block");
            Long length = blockConfigService.queryBlockLength();

            worker.doWork(length);

            Long timeCost = blockConfigService.queryWorkerTimeCost();
            Thread.sleep(timeCost * 1000);
        }
    }


}
