package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.common.Result;
import com.scenic.ai.model.Device;
import com.scenic.ai.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 设备管理控制器
 */
@Tag(name = "设备管理")
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @Operation(summary = "分页查询设备列表")
    @GetMapping("/page")
    public Result<IPage<Device>> pageDevices(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "设备状态") @RequestParam(required = false) String status) {
        Page<Device> page = new Page<>(current, size);
        return Result.ok(deviceService.pageDevices(page, status));
    }

    @Operation(summary = "根据设备编码查询设备")
    @GetMapping("/{deviceCode}")
    public Result<Device> getDeviceByCode(
            @Parameter(description = "设备编码") @PathVariable String deviceCode) {
        return Result.ok(deviceService.getDeviceByCode(deviceCode));
    }

    @Operation(summary = "根据景区名称查询设备列表")
    @GetMapping("/tourism/{tourismName}")
    public Result<List<Device>> getDevicesByTourismName(
            @Parameter(description = "景区名称") @PathVariable String tourismName) {
        return Result.ok(deviceService.getDevicesByTourismName(tourismName));
    }

    @Operation(summary = "根据状态查询设备列表")
    @GetMapping("/status/{status}")
    public Result<List<Device>> getDevicesByStatus(
            @Parameter(description = "设备状态") @PathVariable String status) {
        return Result.ok(deviceService.getDevicesByStatus(status));
    }

    @Operation(summary = "统计各景区设备数量")
    @GetMapping("/count/tourism")
    public Result<List<Map<String, Object>>> countByTourism() {
        return Result.ok(deviceService.countByTourism());
    }

    @Operation(summary = "统计设备状态分布")
    @GetMapping("/count/status")
    public Result<List<Map<String, Object>>> countByStatus() {
        return Result.ok(deviceService.countByStatus());
    }

    @Operation(summary = "根据状态统计设备数量")
    @GetMapping("/count/status/{status}")
    public Result<Long> countDevicesByStatus(
            @Parameter(description = "设备状态") @PathVariable String status) {
        return Result.ok(deviceService.countDevicesByStatus(status));
    }

    @Operation(summary = "批量更新设备状态")
    @PutMapping("/status/batch")
    public Result<Boolean> batchUpdateStatus(
            @Parameter(description = "设备编码数组") @RequestParam String[] deviceCodes,
            @Parameter(description = "新状态") @RequestParam String status) {
        return Result.ok(deviceService.batchUpdateStatus(deviceCodes, status));
    }

    @Operation(summary = "更新设备名称")
    @PutMapping("/{deviceCode}/name")
    public Result<Boolean> updateDeviceName(
            @Parameter(description = "设备编码") @PathVariable String deviceCode,
            @Parameter(description = "新设备名称") @RequestParam String deviceName) {
        return Result.ok(deviceService.updateDeviceName(deviceCode, deviceName));
    }

    @Operation(summary = "更新景区名称")
    @PutMapping("/{deviceCode}/tourism")
    public Result<Boolean> updateTourismName(
            @Parameter(description = "设备编码") @PathVariable String deviceCode,
            @Parameter(description = "新景区名称") @RequestParam String tourismName) {
        return Result.ok(deviceService.updateTourismName(deviceCode, tourismName));
    }

    @Operation(summary = "更新设备状态")
    @PutMapping("/{deviceId}/status")
    public Result<Boolean> updateDeviceStatus(
            @Parameter(description = "设备ID") @PathVariable Long deviceId,
            @Parameter(description = "新状态") @RequestParam String status) {
        return Result.ok(deviceService.updateDeviceStatus(deviceId, status));
    }
} 