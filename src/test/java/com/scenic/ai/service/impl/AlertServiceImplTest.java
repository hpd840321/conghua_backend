package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scenic.ai.mapper.AlertMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 告警服务实现类测试
 *
 * @author AI
 * @date 2023-05-20
 */
public class AlertServiceImplTest {

    @Mock
    private AlertMapper alertMapper;

    @InjectMocks
    private AlertServiceImpl alertService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 测试获取告警概览统计
     */
    @Test
    public void testGetOverview() {
        // 准备测试数据
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        // 模拟数据库查询结果
        Map<String, Object> mockData = new HashMap<>();
        mockData.put("total", 100L);
        mockData.put("handled", 70L);
        mockData.put("pending", 30L);

        when(alertMapper.countByTimeRange(any(), any())).thenReturn(mockData);

        // 执行测试
        Map<String, Object> result = alertService.getOverview(tourismName, deviceCode, startTime, endTime);

        // 验证结果
        assertNotNull(result);
        assertEquals(100L, result.get("totalCount"));
        assertEquals(30L, result.get("pendingCount"));
        assertEquals(70L, result.get("processedCount"));
        assertEquals(70.0, result.get("processRate"));

        // 验证方法调用
        verify(alertMapper).countByTimeRange(any(), any());
    }

    /**
     * 测试获取告警时段分布
     */
    @Test
    public void testGetTimeDistribution() {
        // 准备测试数据
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        // 模拟数据库查询结果
        List<Map<String, Object>> mockData = new ArrayList<>();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("hour", 10);
        data1.put("count", 20L);
        mockData.add(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("hour", 14);
        data2.put("count", 15L);
        mockData.add(data2);

        when(alertMapper.getTimeDistribution(any(), any(), any(), any())).thenReturn(mockData);

        // 执行测试
        Map<String, Object> result = alertService.getTimeDistribution(tourismName, deviceCode, startTime, endTime);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("data"));
        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        assertEquals(2, data.size());

        // 验证方法调用
        verify(alertMapper).getTimeDistribution(any(), any(), any(), any());
    }

    /**
     * 测试获取告警类型分布
     */
    @Test
    public void testGetTypeDistribution() {
        // 准备测试数据
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        // 模拟数据库查询结果
        List<Map<String, Object>> mockData = new ArrayList<>();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("type", "人群聚集");
        data1.put("count", 10L);
        mockData.add(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("type", "异常行为");
        data2.put("count", 5L);
        mockData.add(data2);

        when(alertMapper.getTypeDistribution(any(), any(), any(), any())).thenReturn(mockData);

        // 执行测试
        Map<String, Object> result = alertService.getTypeDistribution(tourismName, deviceCode, startTime, endTime);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("data"));
        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        assertEquals(2, data.size());

        // 验证方法调用
        verify(alertMapper).getTypeDistribution(any(), any(), any(), any());
    }

    /**
     * 测试获取告警级别分布
     */
    @Test
    public void testGetLevelDistribution() {
        // 准备测试数据
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        // 模拟数据库查询结果
        List<Map<String, Object>> mockData = new ArrayList<>();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("level", 1);
        data1.put("count", 15L);
        mockData.add(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("level", 2);
        data2.put("count", 8L);
        mockData.add(data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("level", 3);
        data3.put("count", 3L);
        mockData.add(data3);

        when(alertMapper.getLevelDistribution(any(), any(), any(), any())).thenReturn(mockData);

        // 执行测试
        Map<String, Object> result = alertService.getLevelDistribution(tourismName, deviceCode, startTime, endTime);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("data"));
        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        assertEquals(3, data.size());

        // 验证方法调用
        verify(alertMapper).getLevelDistribution(any(), any(), any(), any());
    }

    /**
     * 测试获取告警趋势
     */
    @Test
    public void testGetTrend() {
        // 准备测试数据
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        // 模拟数据库查询结果
        List<Map<String, Object>> mockData = new ArrayList<>();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("date", "2023-05-01");
        data1.put("count", 5L);
        mockData.add(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("date", "2023-05-02");
        data2.put("count", 8L);
        mockData.add(data2);

        when(alertMapper.selectMaps(any(LambdaQueryWrapper.class))).thenReturn(mockData);

        // 执行测试
        Map<String, Object> result = alertService.getTrend(tourismName, deviceCode, startTime, endTime);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("data"));
        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        assertEquals(2, data.size());

        // 验证方法调用
        verify(alertMapper).selectMaps(any(LambdaQueryWrapper.class));
    }

    /**
     * 测试获取告警设备分布
     */
    @Test
    public void testGetDeviceDistribution() {
        // 准备测试数据
        String tourismName = "测试景区";
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        // 模拟数据库查询结果
        List<Map<String, Object>> mockData = new ArrayList<>();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("deviceCode", "DEVICE001");
        data1.put("count", 10L);
        mockData.add(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("deviceCode", "DEVICE002");
        data2.put("count", 15L);
        mockData.add(data2);

        when(alertMapper.selectMaps(any(LambdaQueryWrapper.class))).thenReturn(mockData);

        // 执行测试
        Map<String, Object> result = alertService.getDeviceDistribution(tourismName, startTime, endTime);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("data"));
        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        assertEquals(2, data.size());

        // 验证方法调用
        verify(alertMapper).selectMaps(any(LambdaQueryWrapper.class));
    }

    /**
     * 测试获取告警景区分布
     */
    @Test
    public void testGetTourismDistribution() {
        // 准备测试数据
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        // 模拟数据库查询结果
        List<Map<String, Object>> mockData = new ArrayList<>();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("tourismName", "景区A");
        data1.put("count", 20L);
        mockData.add(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("tourismName", "景区B");
        data2.put("count", 15L);
        mockData.add(data2);

        when(alertMapper.selectMaps(any(LambdaQueryWrapper.class))).thenReturn(mockData);

        // 执行测试
        Map<String, Object> result = alertService.getTourismDistribution(startTime, endTime);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("data"));
        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        assertEquals(2, data.size());

        // 验证方法调用
        verify(alertMapper).selectMaps(any(LambdaQueryWrapper.class));
    }
} 