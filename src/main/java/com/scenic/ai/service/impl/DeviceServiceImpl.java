package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.DeviceMapper;
import com.scenic.ai.model.Device;
import com.scenic.ai.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备服务实现类
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Override
    public List<Device> list() {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Device::getCreateTime);
        return list(wrapper);
    }

    @Override
    public Device get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("设备ID不能为空");
        }
        return getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("设备信息不能为空");
        }
        return save(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Device device) {
        if (device == null || device.getId() == null) {
            throw new IllegalArgumentException("设备信息不能为空");
        }
        return updateById(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("设备ID不能为空");
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(String deviceCode, Integer status) {
        if (StringUtils.isBlank(deviceCode)) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        if (status == null) {
            throw new IllegalArgumentException("设备状态不能为空");
        }

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceCode, deviceCode);

        Device device = new Device();
        device.setStatus(status);

        return update(device, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateName(String deviceCode, String deviceName) {
        if (StringUtils.isBlank(deviceCode)) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        if (StringUtils.isBlank(deviceName)) {
            throw new IllegalArgumentException("设备名称不能为空");
        }

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceCode, deviceCode);

        Device device = new Device();
        device.setDeviceName(deviceName);

        return update(device, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTourismName(String deviceCode, String tourismName) {
        if (StringUtils.isBlank(deviceCode)) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        if (StringUtils.isBlank(tourismName)) {
            throw new IllegalArgumentException("景区名称不能为空");
        }

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceCode, deviceCode);

        Device device = new Device();
        device.setTourismName(tourismName);

        return update(device, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateStatus(List<String> deviceCodes, Integer status) {
        if (deviceCodes == null || deviceCodes.isEmpty()) {
            throw new IllegalArgumentException("设备编码列表不能为空");
        }
        if (status == null) {
            throw new IllegalArgumentException("设备状态不能为空");
        }

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Device::getDeviceCode, deviceCodes);

        Device device = new Device();
        device.setStatus(status);

        return update(device, wrapper);
    }

    @Override
    public IPage<Device> pageDevices(Integer pageNum, Integer pageSize, String tourismName, Integer status) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq(Device::getTourismName, tourismName);
        }
        if (status != null) {
            wrapper.eq(Device::getStatus, status);
        }

        wrapper.orderByDesc(Device::getCreateTime);

        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public Device getDeviceByCode(String deviceCode) {
        if (StringUtils.isBlank(deviceCode)) {
            throw new IllegalArgumentException("设备编码不能为空");
        }

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceCode, deviceCode);

        return getOne(wrapper);
    }

    @Override
    public List<Device> getDevicesByTourismName(String tourismName) {
        if (StringUtils.isBlank(tourismName)) {
            throw new IllegalArgumentException("景区名称不能为空");
        }

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getTourismName, tourismName);
        wrapper.orderByDesc(Device::getCreateTime);

        return list(wrapper);
    }

    @Override
    public List<Device> getDevicesByStatus(Integer status) {
        if (status == null) {
            throw new IllegalArgumentException("设备状态不能为空");
        }

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getStatus, status);
        wrapper.orderByDesc(Device::getCreateTime);

        return list(wrapper);
    }

    @Override
    public long countByTourism(String tourismName) {
        if (StringUtils.isBlank(tourismName)) {
            throw new IllegalArgumentException("景区名称不能为空");
        }

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getTourismName, tourismName);

        return count(wrapper);
    }

    @Override
    public long countByStatus(Integer status) {
        if (status == null) {
            throw new IllegalArgumentException("设备状态不能为空");
        }

        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getStatus, status);

        return count(wrapper);
    }

    @Override
    public long countDevicesByStatus(String tourismName, Integer status) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq(Device::getTourismName, tourismName);
        }
        if (status != null) {
            wrapper.eq(Device::getStatus, status);
        }

        return count(wrapper);
    }
}