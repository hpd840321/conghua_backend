package com.scenic.ai.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 全景图数据传输对象
 * 用于传输全景图相关信息，包括设备信息、图片信息和区域信息等
 *
 * @author scenic-AI
 * @version 1.0
 */
@Data
public class PanoramaDTO {
    /**
     * 全景图ID
     * 唯一标识一个全景图记录
     */
    private Long id;

    /**
     * 设备编码
     * 关联的设备唯一标识
     */
    private String deviceCode;

    /**
     * 设备名称
     * 设备的显示名称
     */
    private String deviceName;

    /**
     * 景区名称
     * 设备所属的景区名称
     */
    private String tourismName;

    /**
     * 图片URL
     * 全景图的访问地址
     */
    private String imageUrl;

    /**
     * 图片分辨率-宽度
     * 图片的像素宽度
     */
    private Integer width;

    /**
     * 图片分辨率-高度
     * 图片的像素高度
     */
    private Integer height;

    /**
     * 创建时间
     * 记录的创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     * 记录的最后更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 描述信息
     * 全景图的补充说明
     */
    private String description;

    /**
     * 区域列表
     * 全景图中包含的所有区域信息
     */
    private List<RegionDTO> regions;
} 