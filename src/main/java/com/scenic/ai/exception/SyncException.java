package com.scenic.ai.exception;

public class SyncException extends RuntimeException {
    private final SyncStage stage;
    private final String targetId;
    private final int retryCount;

    public SyncException(String message, SyncStage stage, String targetId) {
        super(message);
        this.stage = stage;
        this.targetId = targetId;
        this.retryCount = 0;
    }

    public SyncException(String message, SyncStage stage, String targetId, int retryCount) {
        super(message);
        this.stage = stage;
        this.targetId = targetId;
        this.retryCount = retryCount;
    }

    public SyncException(String message) {
        super(message);
        this.stage = null;
        this.targetId = null;
        this.retryCount = 0;
    }

    public SyncException(String message, Throwable cause) {
        super(message, cause);
        this.stage = null;
        this.targetId = null;
        this.retryCount = 0;
    }

    public SyncException(Throwable cause) {
        super(cause);
        this.stage = null;
        this.targetId = null;
        this.retryCount = 0;
    }

    public SyncException withRetry(int newRetryCount) {
        return new SyncException(getMessage(), stage, targetId, newRetryCount);
    }
    
    /**
     * 获取同步阶段
     * 
     * @return 同步阶段
     */
    public SyncStage getStage() {
        return stage;
    }
    
    /**
     * 获取目标ID
     * 
     * @return 目标ID
     */
    public String getTargetId() {
        return targetId;
    }
    
    /**
     * 获取重试次数
     * 
     * @return 重试次数
     */
    public int getRetryCount() {
        return retryCount;
    }

    public enum SyncStage {
        INIT("初始化"),
        FETCH_STATS("获取统计数据"),
        FETCH_DETAILS("获取详细数据"),
        DOWNLOAD_IMAGE("下载图片"),
        SAVE_DATA("保存数据"),
        VERIFY("数据校验"),
        DATA_CLEANUP("清理过期数据");

        private final String description;

        SyncStage(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
