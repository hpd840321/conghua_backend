package com.scenic.ai.domain.model.alarm;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmData {
    private String id;
    private String type;
    private String level;
    private String status;
    private String content;
    private String areaId;
    private String areaName;
    private LocalDateTime createTime;
    private LocalDateTime handleTime;
    private String handler;
    private String handleResult;
} 