package com.scenic.ai.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全景图详情响应
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PanoramaDetailResponse extends PanoramaResponse {
    /**
     * 分辨率
     */
    private Resolution resolution;
    
    /**
     * 区域列表
     */
    private java.util.List<Region> regions;
    
    @Data
    public static class Resolution {
        private Integer width;
        private Integer height;
    }
    
    @Data
    public static class Region {
        private String id;
        private String name;
        private String type;
        private java.util.List<Coordinate> coordinates;
    }
    
    @Data
    public static class Coordinate {
        private Integer x;
        private Integer y;
    }
} 