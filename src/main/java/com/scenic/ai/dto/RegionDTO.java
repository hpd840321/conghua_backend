package com.scenic.ai.dto;

import com.scenic.ai.common.model.BaseDTO;
import com.scenic.ai.common.validation.ValidationGroups.Create;
import com.scenic.ai.common.validation.ValidationGroups.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 区域数据传输对象
 * 用于传输区域相关信息，包括区域名称、类型和坐标点列表
 *
 * @author scenic-AI
 * @version 1.0
 * @since 2024-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegionDTO extends BaseDTO {
    /**
     * 区域名称
     */
    @NotBlank(message = "区域名称不能为空", groups = {Create.class, Update.class})
    @Size(min = 2, max = 50, message = "区域名称长度必须在2-50个字符之间", groups = {Create.class, Update.class})
    private String name;

    /**
     * 区域类型
     * 例如：监控区域、危险区域等
     */
    @NotBlank(message = "区域类型不能为空", groups = {Create.class, Update.class})
    private String type;

    /**
     * 区域边界坐标点列表
     */
    @NotEmpty(message = "区域坐标点不能为空", groups = {Create.class, Update.class})
    private List<CoordinateDTO> coordinates;
} 