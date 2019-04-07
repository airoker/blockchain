package com.jlu.experiment.block.chain.service;

import com.jlu.experiment.block.chain.common.Constant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

    @Resource
    private LimitStatusService limitStatusService;

    // 不停检测
    @PostConstruct
    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                work();
            }
        }, 3, 3, TimeUnit.SECONDS);
    }

    public void work() {
        System.out.println("work");
        try {
            String status = blockConfigService.queryWorkerStatus();
            if (StringUtils.equals(Constant.WorkerStatus.WORK, status)) {
                logger.info("working, generate one block");
                Long length = blockConfigService.queryBlockLength();

                worker.doWork(length);

                limitStatusService.checkRelease();

                Long timeCost = blockConfigService.queryWorkerTimeCost();
                Thread.sleep(timeCost * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
