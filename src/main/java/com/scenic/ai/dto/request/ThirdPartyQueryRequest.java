package com.scenic.ai.dto.request;

import java.util.Objects;

/**
 * 第三方接口查询请求DTO
 */
public class ThirdPartyQueryRequest {
    /**
     * 页码(默认1)
     */
    private Integer pageNo = 1;

    /**
     * 分页大小(默认10)
     */
    private Integer pageSize = 10;

    /**
     * 景区名称
     */
    private String tourismName;

    /**
     * 数据来源
     */
    private String odsSource;

    /**
     * 算法类型
     */
    private String algName;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 搜索开始日期(yyyy-MM-dd)
     */
    private String searchBeginDate;

    /**
     * 搜索结束日期(yyyy-MM-dd)
     */
    private String searchEndDate;
    
    public Integer getPageNo() {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public String getTourismName() {
        return tourismName;
    }
    
    public void setTourismName(String tourismName) {
        this.tourismName = tourismName;
    }
    
    public String getOdsSource() {
        return odsSource;
    }
    
    public void setOdsSource(String odsSource) {
        this.odsSource = odsSource;
    }
    
    public String getAlgName() {
        return algName;
    }
    
    public void setAlgName(String algName) {
        this.algName = algName;
    }
    
    public String getDeviceCode() {
        return deviceCode;
    }
    
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
    
    public String getSearchBeginDate() {
        return searchBeginDate;
    }
    
    public void setSearchBeginDate(String searchBeginDate) {
        this.searchBeginDate = searchBeginDate;
    }
    
    public String getSearchEndDate() {
        return searchEndDate;
    }
    
    public void setSearchEndDate(String searchEndDate) {
        this.searchEndDate = searchEndDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThirdPartyQueryRequest that = (ThirdPartyQueryRequest) o;
        return Objects.equals(pageNo, that.pageNo) &&
               Objects.equals(pageSize, that.pageSize) &&
               Objects.equals(tourismName, that.tourismName) &&
               Objects.equals(odsSource, that.odsSource) &&
               Objects.equals(algName, that.algName) &&
               Objects.equals(deviceCode, that.deviceCode) &&
               Objects.equals(searchBeginDate, that.searchBeginDate) &&
               Objects.equals(searchEndDate, that.searchEndDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(pageNo, pageSize, tourismName, odsSource, algName, deviceCode, searchBeginDate, searchEndDate);
    }
    
    @Override
    public String toString() {
        return "ThirdPartyQueryRequest{" +
               "pageNo=" + pageNo +
               ", pageSize=" + pageSize +
               ", tourismName='" + tourismName + '\'' +
               ", odsSource='" + odsSource + '\'' +
               ", algName='" + algName + '\'' +
               ", deviceCode='" + deviceCode + '\'' +
               ", searchBeginDate='" + searchBeginDate + '\'' +
               ", searchEndDate='" + searchEndDate + '\'' +
               '}';
    }
} 