package com.jlu.experiment.block.chain.model;

/**
 * description:
 * <p>
 * Created by linge on 19/4/7.
 */
public class WorkerStatusVo {
    private Long unprocessCount;
    private Long processedCount;
    private Long blockCount;

    public Long getUnprocessCount() {
        return unprocessCount;
    }

    public void setUnprocessCount(Long unprocessCount) {
        this.unprocessCount = unprocessCount;
    }

    public Long getProcessedCount() {
        return processedCount;
    }

    public void setProcessedCount(Long processedCount) {
        this.processedCount = processedCount;
    }

    public Long getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(Long blockCount) {
        this.blockCount = blockCount;
    }
}
