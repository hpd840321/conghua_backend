package com.scenic.ai.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Device {
    private Long id;
    private String code;
    private String name;
    private String type;
    private String status;
    private String areaId;
    private String areaName;
    private String location;
    private Double longitude;
    private Double latitude;
    private String ip;
    private Integer port;
    private String manufacturer;
    private String model;
    private String version;
    private LocalDateTime lastHeartbeatTime;
    private LocalDateTime installTime;
    private String installPerson;
    private String maintainPerson;
    private String maintainPhone;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 