package com.scenic.ai.domain.model.alarm;

import com.scenic.ai.common.QueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlarmQueryParams extends QueryParams {
    private String type;
    private String level;
    private String areaId;
} 