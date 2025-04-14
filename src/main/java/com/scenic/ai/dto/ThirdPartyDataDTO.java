package com.scenic.ai.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 第三方数据传输对象
 * 用于接收和处理第三方系统提供的数据
 *
 * @author scenic-AI
 * @version 1.0
 */
@Data
public class ThirdPartyDataDTO {
    
    /**
     * 唯一标识符
     */
    private Long id;
    
    /**
     * 设备编号
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
     * 告警事件
     */
    private String alarmEvent;
    
    /**
     * 图片数据
     */
    private String image;
    
    /**
     * 人数统计
     */
    private Integer count;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 