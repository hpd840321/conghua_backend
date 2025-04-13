package com.conghua.tourism.common.api;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PageRequest {
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页条数最小为1")
    private Integer pageSize = 10;
} 