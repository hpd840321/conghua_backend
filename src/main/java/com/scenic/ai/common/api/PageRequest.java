package com.scenic.ai.common.api;

import jakarta.validation.constraints.Min;
import lombok.Data;


@Data
public class PageRequest {
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页条数最小为1")
    private Integer pageSize = 10;
} 