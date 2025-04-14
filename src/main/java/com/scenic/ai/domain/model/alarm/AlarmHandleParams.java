package com.scenic.ai.domain.model.alarm;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class AlarmHandleParams {
    @NotBlank(message = "告警ID不能为空")
    private String id;
    
    @NotBlank(message = "处理结果不能为空")
    private String handleResult;
    
    @NotBlank(message = "处理人不能为空")
    private String handler;
    
    private String remark;
} 