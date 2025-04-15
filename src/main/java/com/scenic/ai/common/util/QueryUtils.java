package com.scenic.ai.common.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.scenic.ai.model.FlowAnalysis;

import java.time.LocalDateTime;

/**
 * 查询条件构建工具类
 */
public class QueryUtils {
    /**
     * 构建分页查询条件
     */
    public static LambdaQueryWrapper<FlowAnalysis> buildPageQueryWrapper(
            String deviceCode,
            String tourismName,
            LocalDateTime startTime,
            LocalDateTime endTime) {
        LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();

        // 设备编码条件
        if (StringUtils.isNotBlank(deviceCode)) {
            wrapper.eq(FlowAnalysis::getDeviceCode, deviceCode);
        }

        // 景区名称条件
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq(FlowAnalysis::getTourismName, tourismName);
        }

        // 时间范围条件
        if (startTime != null && endTime != null) {
            wrapper.between(FlowAnalysis::getRecordTime, startTime, endTime);
        }

        // 默认按记录时间倒序
        wrapper.orderByDesc(FlowAnalysis::getRecordTime);

        return wrapper;
    }

    /**
     * 构建设备查询条件
     */
    public static LambdaQueryWrapper<FlowAnalysis> buildDeviceQueryWrapper(
            String deviceCode,
            LocalDateTime startTime,
            LocalDateTime endTime) {
        LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlowAnalysis::getDeviceCode, deviceCode);

        if (startTime != null && endTime != null) {
            wrapper.between(FlowAnalysis::getRecordTime, startTime, endTime);
        }

        wrapper.orderByDesc(FlowAnalysis::getRecordTime);
        return wrapper;
    }

    /**
     * 构建景区查询条件
     */
    public static LambdaQueryWrapper<FlowAnalysis> buildTourismQueryWrapper(
            String tourismName,
            LocalDateTime startTime,
            LocalDateTime endTime) {
        LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlowAnalysis::getTourismName, tourismName);

        if (startTime != null && endTime != null) {
            wrapper.between(FlowAnalysis::getRecordTime, startTime, endTime);
        }

        wrapper.orderByDesc(FlowAnalysis::getRecordTime);
        return wrapper;
    }
}