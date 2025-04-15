package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scenic.ai.model.Device;
import com.scenic.ai.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 设备管理控制器
 */
@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);
    
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * 获取设备列表
     */
    @GetMapping
    public ResponseEntity<List<Device>> list() {
        return ResponseEntity.ok(deviceService.list());
    }

    /**
     * 获取设备详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Device> get(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.get(id));
    }

    /**
     * 添加设备
     */
    @PostMapping
    public ResponseEntity<Boolean> add(@RequestBody Device device) {
        return ResponseEntity.ok(deviceService.add(device));
    }

    /**
     * 更新设备
     */
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody Device device) {
        device.setId(id);
        return ResponseEntity.ok(deviceService.update(device));
    }

    /**
     * 删除设备
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.delete(id));
    }

    /**
     * 更新设备状态
     */
    @PutMapping("/status")
    public ResponseEntity<Boolean> updateStatus(
            @RequestParam String deviceCode,
            @RequestParam Integer status) {
        return ResponseEntity.ok(deviceService.updateStatus(deviceCode, status));
    }

    /**
     * 更新设备名称
     */
    @PutMapping("/name")
    public ResponseEntity<Boolean> updateName(
            @RequestParam String deviceCode,
            @RequestParam String deviceName) {
        return ResponseEntity.ok(deviceService.updateName(deviceCode, deviceName));
    }

    /**
     * 更新景区名称
     */
    @PutMapping("/tourism")
    public ResponseEntity<Boolean> updateTourismName(
            @RequestParam String deviceCode,
            @RequestParam String tourismName) {
        return ResponseEntity.ok(deviceService.updateTourismName(deviceCode, tourismName));
    }

    /**
     * 批量更新设备状态
     */
    @PutMapping("/batch/status")
    public ResponseEntity<Boolean> batchUpdateStatus(
            @RequestParam String deviceCodes,
            @RequestParam Integer status) {
        List<String> deviceCodeList = Arrays.asList(deviceCodes.split(","));
        return ResponseEntity.ok(deviceService.batchUpdateStatus(deviceCodeList, status));
    }

    /**
     * 分页查询设备列表
     */
    @GetMapping("/page")
    public ResponseEntity<IPage<Device>> pageDevices(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) Integer status) {
        return ResponseEntity.ok(deviceService.pageDevices(pageNum, pageSize, tourismName, status));
    }

    /**
     * 根据设备编码查询设备
     */
    @GetMapping("/code/{deviceCode}")
    public ResponseEntity<Device> getDeviceByCode(
            @PathVariable String deviceCode) {
        return ResponseEntity.ok(deviceService.getDeviceByCode(deviceCode));
    }

    /**
     * 根据景区名称查询设备列表
     */
    @GetMapping("/tourism/{tourismName}")
    public ResponseEntity<List<Device>> getDevicesByTourismName(
            @PathVariable String tourismName) {
        return ResponseEntity.ok(deviceService.getDevicesByTourismName(tourismName));
    }

    /**
     * 根据状态查询设备列表
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Device>> getDevicesByStatus(
            @PathVariable Integer status) {
        return ResponseEntity.ok(deviceService.getDevicesByStatus(status));
    }

    /**
     * 统计景区设备数量
     */
    @GetMapping("/count/tourism/{tourismName}")
    public ResponseEntity<Long> countByTourism(
            @PathVariable String tourismName) {
        return ResponseEntity.ok(deviceService.countByTourism(tourismName));
    }

    /**
     * 统计设备状态分布
     */
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countByStatus(
            @PathVariable Integer status) {
        return ResponseEntity.ok(deviceService.countByStatus(status));
    }

    /**
     * 根据状态统计设备数量
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDevicesByStatus(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) Integer status) {
        return ResponseEntity.ok(deviceService.countDevicesByStatus(tourismName, status));
    }
} 