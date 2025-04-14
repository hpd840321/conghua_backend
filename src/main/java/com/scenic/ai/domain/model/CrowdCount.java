package com.scenic.ai.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CrowdCount {
    private Long id;
    private String areaId;
    private Integer count;
    private LocalDateTime countTime;
    private String panoramicImage;
    private Double density;
} 