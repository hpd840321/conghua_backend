package com.scenic.ai.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 全景图响应
 */
@Data
public class PanoramaResponse {
    /**
     * 全景图ID
     */
    private String id;
    
    /**
     * 图片URL
     */
    private String imageUrl;
    
    /**
     * 设备编码
     */
    private String deviceCode;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * 景区名称
     */
    private String tourismName;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 描述信息
     */
    private String description;
} 