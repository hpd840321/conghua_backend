package com.scenic.ai.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class PanoramaQueryParams {
    private String keyword;
    private String deviceCode;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    private Integer pageSize = 10;
    private Integer current = 1;
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public String getDeviceCode() {
        return deviceCode;
    }
    
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public Integer getCurrent() {
        return current;
    }
    
    public void setCurrent(Integer current) {
        this.current = current;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanoramaQueryParams that = (PanoramaQueryParams) o;
        return Objects.equals(keyword, that.keyword) &&
               Objects.equals(deviceCode, that.deviceCode) &&
               Objects.equals(startTime, that.startTime) &&
               Objects.equals(endTime, that.endTime) &&
               Objects.equals(pageSize, that.pageSize) &&
               Objects.equals(current, that.current);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(keyword, deviceCode, startTime, endTime, pageSize, current);
    }
    
    @Override
    public String toString() {
        return "PanoramaQueryParams{" +
               "keyword='" + keyword + '\'' +
               ", deviceCode='" + deviceCode + '\'' +
               ", startTime=" + startTime +
               ", endTime=" + endTime +
               ", pageSize=" + pageSize +
               ", current=" + current +
               '}';
    }
} 