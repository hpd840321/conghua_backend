package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.entity.Alert;
import com.scenic.ai.entity.AlertHandleRecord;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 告警服务接口
 * 提供告警信息的创建、更新、查询、统计等功能
 *
 * @author AI
 * @date 2024-03-20
 */
public interface IAlertService extends IService<Alert> {

        /**
         * 创建告警
         *
         * @param alert 告警信息
         * @return 是否创建成功
         */
        boolean createAlert(Alert alert);

        /**
         * 更新告警状态
         *
         * @param id     告警ID
         * @param status 状态（0-待处理，1-已处理）
         * @return 是否更新成功
         */
        boolean updateStatus(Long id, Integer status);

        /**
         * 批量更新告警状态
         *
         * @param ids    告警ID列表
         * @param status 状态（0-待处理，1-已处理）
         * @return 是否更新成功
         */
        boolean batchUpdateStatus(List<Long> ids, Integer status);

        /**
         * 分页查询告警信息
         *
         * @param page        分页参数
         * @param tourismName 景区名称（可选）
         * @param deviceCode  设备编码（可选）
         * @param alertType   告警类型（可选）
         * @param alertLevel  告警级别（可选，1-低，2-中，3-高）
         * @param alertStatus 告警状态（可选，0-待处理，1-已处理）
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 分页结果
         */
        Page<Alert> getAlertPage(Page<Alert> page, String tourismName, String deviceCode,
                        String alertType, Integer alertLevel, Integer alertStatus,
                        LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取告警时段分布
         *
         * @param tourismName 景区名称（可选）
         * @param deviceCode  设备编码（可选）
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 时段分布数据
         */
        Map<String, Object> getTimeDistribution(String tourismName, String deviceCode,
                        LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取告警类型分布
         *
         * @param tourismName 景区名称（可选）
         * @param deviceCode  设备编码（可选）
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 类型分布数据
         */
        Map<String, Object> getTypeDistribution(String tourismName, String deviceCode,
                        LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取告警级别分布
         *
         * @param tourismName 景区名称（可选）
         * @param deviceCode  设备编码（可选）
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 级别分布数据
         */
        Map<String, Object> getLevelDistribution(String tourismName, String deviceCode,
                        LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取告警概览统计
         *
         * @param tourismName 景区名称（可选）
         * @param deviceCode  设备编码（可选）
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 概览统计数据
         */
        Map<String, Object> getOverview(String tourismName, String deviceCode,
                        LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取设备最新告警
         *
         * @param deviceCode 设备编码
         * @return 最新告警信息
         */
        Alert getLatestByDevice(String deviceCode);

        /**
         * 处理告警
         *
         * @param alertId     告警ID
         * @param description 处理描述
         * @return 是否处理成功
         */
        boolean handleAlert(Long alertId, String description);

        /**
         * 批量处理告警
         *
         * @param ids 告警ID列表
         * @return 是否处理成功
         */
        boolean batchHandleAlerts(List<Long> ids);

        /**
         * 统计待处理告警数量
         *
         * @param tourismName 景区名称（可选）
         * @return 待处理告警数量
         */
        int countPendingAlerts(String tourismName);

        /**
         * 获取设备告警列表
         *
         * @param deviceCode 设备编码
         * @param startTime  开始时间
         * @param endTime    结束时间
         * @return 告警列表
         */
        List<Alert> getByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取景区告警列表
         *
         * @param tourismName 景区名称
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警列表
         */
        List<Alert> getByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取告警详情
         *
         * @param id 告警ID
         * @return 告警详情
         */
        Alert getAlertDetail(Long id);

        /**
         * 获取告警类型列表
         *
         * @return 告警类型列表
         */
        List<Map<String, Object>> getAlertTypes();

        /**
         * 获取告警趋势
         *
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 趋势数据
         */
        Map<String, Object> getTrend(String tourismName, String deviceCode,
                        LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取告警设备分布
         *
         * @param tourismName 景区名称
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 设备分布数据
         */
        Map<String, Object> getDeviceDistribution(String tourismName,
                        LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取告警景区分布
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 景区分布数据
         */
        Map<String, Object> getTourismDistribution(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 分页查询告警信息
         *
         * @param params 查询参数
         * @return 告警列表
         */
        List<Alert> pageAlerts(Map<String, Object> params);

        /**
         * 统计告警数量
         *
         * @param params 查询参数
         * @return 告警数量
         */
        Long countAlerts(Map<String, Object> params);

        /**
         * 获取未处理的告警列表
         *
         * @param deviceCode  设备编码
         * @param tourismName 景区名称
         * @return 未处理的告警列表
         */
        List<Alert> getUnhandledAlerts(String deviceCode, String tourismName);

        /**
         * 根据级别和状态统计告警数量
         *
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @return 告警数量
         */
        int countByLevelAndStatus(Integer alertLevel, Integer alertStatus);

        /**
         * 统计未处理的告警数量
         *
         * @param deviceCode  设备编码
         * @param tourismName 景区名称
         * @return 未处理的告警数量
         */
        int countUnhandledAlerts(String deviceCode, String tourismName);

        /**
         * 处理告警
         *
         * @param id 告警ID
         * @return 是否处理成功
         */
        boolean processAlert(Long id);

        /**
         * 分页查询告警信息
         *
         * @param pageNum     页码
         * @param pageSize    每页大小
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 分页结果
         */
        IPage<Alert> page(Integer pageNum, Integer pageSize, String tourismName, String deviceCode,
                        String alertType, Integer alertLevel, Integer alertStatus,
                        LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取告警列表
         *
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @param pageNum     页码
         * @param pageSize    每页大小
         * @return 告警列表
         */
        Map<String, Object> getAlertList(String tourismName, String deviceCode, String alertType,
                        Integer alertLevel, Integer alertStatus, LocalDateTime startTime,
                        LocalDateTime endTime, Integer pageNum, Integer pageSize);

        /**
         * 获取告警统计信息
         *
         * @param tourismName 景区名称
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 统计信息
         */
        Map<String, Object> getAlertStatistics(String tourismName, LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 根据ID查询告警
         * 
         * @param id 告警ID
         * @return 告警信息
         */
        Alert getById(Long id);

        /**
         * 根据设备编码查询告警列表
         * 
         * @param deviceCode 设备编码
         * @return 告警列表
         */
        List<Alert> listByDevice(String deviceCode);

        /**
         * 根据景区名称查询告警列表
         * 
         * @param tourismName 景区名称
         * @return 告警列表
         */
        List<Alert> listByTourism(String tourismName);

        /**
         * 根据告警级别和状态查询告警列表
         * 
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @return 告警列表
         */
        List<Alert> listByLevelAndStatus(Integer alertLevel, Integer alertStatus);

        /**
         * 根据告警状态查询告警列表
         * 
         * @param alertStatus 告警状态
         * @return 告警列表
         */
        List<Alert> listByStatus(Integer alertStatus);

        /**
         * 根据条件查询告警列表
         * 
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警列表
         */
        List<Alert> listByConditions(String tourismName, String deviceCode, String alertType,
                        Integer alertLevel, Integer alertStatus, LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 根据条件分页查询告警
         * 
         * @param pageNum     页码
         * @param pageSize    每页大小
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 分页结果
         */
        IPage<Alert> pageByConditions(Integer pageNum, Integer pageSize, String tourismName,
                        String deviceCode, String alertType, Integer alertLevel, Integer alertStatus,
                        LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 根据条件统计告警数量
         * 
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警数量
         */
        int countByConditions(String tourismName, String deviceCode, String alertType,
                        Integer alertLevel, Integer alertStatus, LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 根据时间范围统计告警数量
         * 
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 告警数量
         */
        int countByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 根据时间范围统计告警类型分布
         * 
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 告警类型分布
         */
        List<Map<String, Object>> countByTypeAndTimeRange(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 根据时间范围统计告警级别分布
         * 
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 告警级别分布
         */
        List<Map<String, Object>> countByLevelAndTimeRange(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 根据时间范围统计告警状态分布
         * 
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 告警状态分布
         */
        List<Map<String, Object>> countByStatusAndTimeRange(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 根据时间范围统计景区告警分布
         * 
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 景区告警分布
         */
        List<Map<String, Object>> countByTourismAndTimeRange(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 根据时间范围统计设备告警分布
         * 
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 设备告警分布
         */
        List<Map<String, Object>> countByDeviceAndTimeRange(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取告警处理记录
         * 
         * @param alertId 告警ID
         * @return 处理记录列表
         */
        List<AlertHandleRecord> getHandleRecords(Long alertId);

        /**
         * 分页查询告警信息
         *
         * @param pageNum     页码
         * @param pageSize    每页大小
         * @param deviceCode  设备编码
         * @param tourismName 景区名称
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警列表
         */
        List<Alert> page(Integer pageNum, Integer pageSize, String deviceCode, String tourismName,
                        String alertType, Integer alertLevel, Integer alertStatus,
                        Date startTime, Date endTime);

        /**
         * 统计告警数量
         *
         * @param deviceCode  设备编码
         * @param tourismName 景区名称
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警数量
         */
        int count(String deviceCode, String tourismName, String alertType,
                        Integer alertLevel, Integer alertStatus, Date startTime, Date endTime);

        /**
         * 根据设备编码查询告警列表
         * 
         * @param deviceCode 设备编码
         * @return 告警列表
         */
        List<Alert> selectByDeviceCode(String deviceCode);

        /**
         * 根据景区名称查询告警列表
         * 
         * @param tourismName 景区名称
         * @return 告警列表
         */
        List<Alert> selectByTourismName(String tourismName);

        /**
         * 根据时间范围查询告警列表
         * 
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 告警列表
         */
        List<Alert> selectByTimeRange(Date startTime, Date endTime);
}