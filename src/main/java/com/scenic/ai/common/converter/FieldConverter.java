package com.scenic.ai.common.converter;

import com.scenic.ai.dto.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * 字段映射转换工具类
 * 用于处理前后端字段的转换
 *
 * @author scenic-AI
 * @version 1.0
 */
@Component
public class FieldConverter {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 日期时间转换为字符串
     */
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_FORMATTER) : null;
    }
    
    /**
     * 字符串转换为日期时间
     */
    public static LocalDateTime stringToDateTime(String dateStr) {
        return dateStr != null ? LocalDateTime.parse(dateStr, DATE_FORMATTER) : null;
    }

    /**
     * 前端分页参数转换
     */
    public static class PageConverter {
        public static Integer getCurrentToPageNo(Integer current) {
            return current != null ? current : 1;
        }
        
        public static Integer getPageSize(Integer pageSize) {
            return pageSize != null ? pageSize : 10;
        }
    }

    /**
     * 告警相关字段转换
     */
    public static class AlertConverter {
        public static String getAlertTypeFromEvent(String alarmEvent) {
            return alarmEvent; // 直接映射，后续可添加映射逻辑
        }
        
        public static String getEventFromAlertType(String alertType) {
            return alertType; // 直接映射，后续可添加映射逻辑
        }
    }

    /**
     * 全景图相关字段转换
     */
    public static class PanoramaConverter {
        public static String getImageUrlFromImage(String image) {
            return image; // 直接映射，后续可添加URL处理逻辑
        }
        
        public static String getImageFromImageUrl(String imageUrl) {
            return imageUrl; // 直接映射，后续可添加URL处理逻辑
        }
    }

    /**
     * 人群统计相关字段转换
     */
    public static class CrowdConverter {
        public static Integer getCrowdCountFromCount(Integer count) {
            return count; // 直接映射，后续可添加计算逻辑
        }
        
        public static Integer getCountFromCrowdCount(Integer crowdCount) {
            return crowdCount; // 直接映射，后续可添加计算逻辑
        }
    }

    /**
     * 查询参数转换
     */
    public static class QueryConverter {
        public static String getSearchBeginDateFromStartTime(String startTime) {
            return startTime; // 直接映射，后续可添加日期格式转换
        }
        
        public static String getSearchEndDateFromEndTime(String endTime) {
            return endTime; // 直接映射，后续可添加日期格式转换
        }
        
        public static String getAlgNameFromAlgorithmType(String algorithmType) {
            return algorithmType; // 直接映射，后续可添加映射逻辑
        }
    }
} 