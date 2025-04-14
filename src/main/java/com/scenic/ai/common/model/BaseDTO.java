package com.scenic.ai.common.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 基础数据传输对象
 * 包含所有DTO的通用字段
 *
 * @author scenic-AI
 * @version 1.0
 * @since 2024-01
 */
@Data
public class BaseDTO {
    /**
     * 唯一标识符
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 