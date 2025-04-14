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
 * 设备Mapper接口
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
    /**
     * 根据景区名称查询设备列表
     */
    List<Device> findByTourismName(@Param("tourismName") String tourismName);

    /**
     * 根据状态查询设备列表
     */
    List<Device> findByStatus(@Param("status") String status);

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
     * 条件分页查询
     */
    List<Device> findByConditions(@Param("params") Map<String, Object> params);

    /**
     * 查询设备总数
     */
    Long countDevices(@Param("params") Map<String, Object> params);

    /**
     * 批量更新设备状态
     * @param deviceCodes 设备编码列表
     * @param status 状态
     * @return 更新记录数
     */
    @Update("UPDATE CLOUDWALK.DEVICE SET STATUS = #{status}, UPDATE_TIME = SYSTIMESTAMP " +
            "WHERE DEVICE_CODE IN (SELECT UNNEST(#{deviceCodes}))")
    int batchUpdateStatus(@Param("deviceCodes") String[] deviceCodes, @Param("status") String status);

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