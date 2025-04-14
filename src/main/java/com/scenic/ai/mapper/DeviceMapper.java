package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 设备管理Mapper接口
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
    
    /**
     * 查询设备总数
     *
     * @param params 查询参数
     * @return 设备总数
     */
    Long countDevices(@Param("params") Map<String, Object> params);
    
    /**
     * 查询设备列表
     *
     * @param params 查询参数
     * @return 设备列表
     */
    List<Device> selectDevices(@Param("params") Map<String, Object> params);
    
    /**
     * 根据ID查询设备
     *
     * @param id 设备ID
     * @return 设备信息
     */
    Device selectById(@Param("id") Long id);
    
    /**
     * 根据景区名称查询设备列表
     *
     * @param tourismName 景区名称
     * @return 设备列表
     */
    List<Device> selectByTourismName(@Param("tourismName") String tourismName);
    
    /**
     * 根据设备状态查询设备列表
     *
     * @param status 设备状态
     * @return 设备列表
     */
    List<Device> selectByStatus(@Param("status") String status);
    
    /**
     * 批量更新设备状态
     *
     * @param deviceCodes 设备编码列表
     * @param status 设备状态
     * @return 更新成功的记录数
     */
    int updateStatusBatch(@Param("deviceCodes") List<String> deviceCodes, @Param("status") String status);
    
    /**
     * 条件分页查询
     *
     * @param params 查询参数
     * @return 设备列表
     */
    List<Device> findByConditions(@Param("params") Map<String, Object> params);

    /**
     * 统计各景区设备数量
     */
    @Select("SELECT TOURISM_NAME as tourismName, COUNT(*) as count " +
            "FROM CLOUDWALK.DEVICE " +
            "WHERE TOURISM_NAME IS NOT NULL " +
            "GROUP BY TOURISM_NAME")
    List<Map<String, Object>> countByTourism();

    /**
     * 统计设备状态分布
     */
    @Select("SELECT STATUS as status, COUNT(*) as count " +
            "FROM CLOUDWALK.DEVICE " +
            "GROUP BY STATUS")
    List<Map<String, Object>> countByStatus();

    /**
     * 更新设备名称
     * @param deviceCode 设备编码
     * @param deviceName 设备名称
     * @return 更新记录数
     */
    @Update("UPDATE CLOUDWALK.DEVICE SET DEVICE_NAME = #{deviceName}, UPDATE_TIME = SYSTIMESTAMP " +
            "WHERE DEVICE_CODE = #{deviceCode}")
    int updateDeviceName(@Param("deviceCode") String deviceCode, @Param("deviceName") String deviceName);

    /**
     * 更新景区名称
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @return 更新记录数
     */
    @Update("UPDATE CLOUDWALK.DEVICE SET TOURISM_NAME = #{tourismName}, UPDATE_TIME = SYSTIMESTAMP " +
            "WHERE DEVICE_CODE = #{deviceCode}")
    int updateTourismName(@Param("deviceCode") String deviceCode, @Param("tourismName") String tourismName);
} 