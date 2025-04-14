package com.scenic.ai.domain.model.alarm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlarmConfig {
    @NotBlank(message = "告警类型不能为空")
    private String type;
    
    @NotBlank(message = "告警级别不能为空")
    private String level;
    
    @NotNull(message = "告警阈值不能为空")
    private Double threshold;
    
    private String description;
    private Boolean enabled = true;
    private String notifyType;
    private String notifyTarget;
} 