package com.scenic.ai.domain.model.alarm;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlarmConfigResponse extends AlarmConfig {
    private String id;
    private Long createTime;
    private Long updateTime;
    private String creator;
    private String updater;
} 