package com.scenic.ai.dto;

import com.scenic.ai.common.validation.ValidationGroups.Create;
import com.scenic.ai.common.validation.ValidationGroups.Update;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 坐标点数据传输对象
 * 用于传输坐标点信息，包括经度和纬度
 *
 * @author scenic-AI
 * @version 1.0
 * @since 2024-01
 */
@Data
public class CoordinateDTO {
    /**
     * 经度
     * 取值范围：-180.0 到 180.0
     */
    @NotNull(message = "经度不能为空", groups = {Create.class, Update.class})
    @DecimalMin(value = "-180.0", message = "经度不能小于-180度", groups = {Create.class, Update.class})
    @DecimalMax(value = "180.0", message = "经度不能大于180度", groups = {Create.class, Update.class})
    private BigDecimal longitude;

    /**
     * 纬度
     * 取值范围：-90.0 到 90.0
     */
    @NotNull(message = "纬度不能为空", groups = {Create.class, Update.class})
    @DecimalMin(value = "-90.0", message = "纬度不能小于-90度", groups = {Create.class, Update.class})
    @DecimalMax(value = "90.0", message = "纬度不能大于90度", groups = {Create.class, Update.class})
    private BigDecimal latitude;
} 