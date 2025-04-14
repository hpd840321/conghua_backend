package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scenic.ai.common.Result;
import com.scenic.ai.model.Device;
import com.scenic.ai.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 设备管理控制器
 */
@Api(tags = "设备管理")
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @ApiOperation("获取设备列表")
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(deviceService.list());
    }

    @ApiOperation("获取设备详情")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@ApiParam("设备ID") @PathVariable Long id) {
        return ResponseEntity.ok(deviceService.get(id));
    }

    @ApiOperation("添加设备")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Device device) {
        return ResponseEntity.ok(deviceService.add(device));
    }

    @ApiOperation("更新设备")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@ApiParam("设备ID") @PathVariable Long id, @RequestBody Device device) {
        device.setId(id);
        return ResponseEntity.ok(deviceService.update(device));
    }

    @ApiOperation("删除设备")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@ApiParam("设备ID") @PathVariable Long id) {
        return ResponseEntity.ok(deviceService.delete(id));
    }

    @ApiOperation("更新设备状态")
    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(
            @ApiParam("设备编码") @RequestParam String deviceCode,
            @ApiParam("设备状态") @RequestParam Integer status) {
        return ResponseEntity.ok(deviceService.updateStatus(deviceCode, status));
    }

    @ApiOperation("更新设备名称")
    @PutMapping("/name")
    public ResponseEntity<?> updateName(
            @ApiParam("设备编码") @RequestParam String deviceCode,
            @ApiParam("设备名称") @RequestParam String deviceName) {
        return ResponseEntity.ok(deviceService.updateName(deviceCode, deviceName));
    }

    @ApiOperation("更新景区名称")
    @PutMapping("/tourism")
    public ResponseEntity<?> updateTourismName(
            @ApiParam("设备编码") @RequestParam String deviceCode,
            @ApiParam("景区名称") @RequestParam String tourismName) {
        return ResponseEntity.ok(deviceService.updateTourismName(deviceCode, tourismName));
    }

    @ApiOperation("批量更新设备状态")
    @PutMapping("/batch/status")
    public ResponseEntity<?> batchUpdateStatus(
            @ApiParam("设备编码列表") @RequestParam String deviceCodes,
            @ApiParam("设备状态") @RequestParam Integer status) {
        List<String> deviceCodeList = Arrays.asList(deviceCodes.split(","));
        return ResponseEntity.ok(deviceService.batchUpdateStatus(deviceCodeList, status));
    }

    @ApiOperation("分页查询设备列表")
    @GetMapping("/page")
    public Result<IPage<Device>> pageDevices(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("设备状态") @RequestParam(required = false) Integer status) {
        return Result.ok(deviceService.pageDevices(pageNum, pageSize, tourismName, status));
    }

    @ApiOperation("根据设备编码查询设备")
    @GetMapping("/code/{deviceCode}")
    public Result<Device> getDeviceByCode(
            @ApiParam("设备编码") @PathVariable String deviceCode) {
        return Result.ok(deviceService.getDeviceByCode(deviceCode));
    }

    @ApiOperation("根据景区名称查询设备列表")
    @GetMapping("/tourism/{tourismName}")
    public Result<List<Device>> getDevicesByTourismName(
            @ApiParam("景区名称") @PathVariable String tourismName) {
        return Result.ok(deviceService.getDevicesByTourismName(tourismName));
    }

    @ApiOperation("根据状态查询设备列表")
    @GetMapping("/status/{status}")
    public Result<List<Device>> getDevicesByStatus(
            @ApiParam("设备状态") @PathVariable Integer status) {
        return Result.ok(deviceService.getDevicesByStatus(status));
    }

    @ApiOperation("统计景区设备数量")
    @GetMapping("/count/tourism/{tourismName}")
    public Result<Long> countByTourism(
            @ApiParam("景区名称") @PathVariable String tourismName) {
        return Result.ok(deviceService.countByTourism(tourismName));
    }

    @ApiOperation("统计设备状态分布")
    @GetMapping("/count/status/{status}")
    public Result<Long> countByStatus(
            @ApiParam("设备状态") @PathVariable Integer status) {
        return Result.ok(deviceService.countByStatus(status));
    }

    @ApiOperation("根据状态统计设备数量")
    @GetMapping("/count")
    public Result<Long> countDevicesByStatus(
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("设备状态") @RequestParam(required = false) Integer status) {
        return Result.ok(deviceService.countDevicesByStatus(tourismName, status));
    }
} 