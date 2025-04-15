package com.scenic.ai.controller;

import com.scenic.ai.service.IAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 告警统计控制器测试类
 *
 * @author AI
 * @date 2023-05-20
 */
public class AlertStatisticsControllerTest {

    @Mock
    private IAlertService alertService;

    @InjectMocks
    private AlertStatisticsController alertStatisticsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(alertStatisticsController).build();
    }

    /**
     * 测试获取告警统计概览
     */
    @Test
    public void testGetOverview() throws Exception {
        // 准备测试数据
        Map<String, Object> overview = new HashMap<>();
        overview.put("totalCount", 100);
        overview.put("pendingCount", 30);
        overview.put("processedCount", 70);
        overview.put("processRate", 70.0);

        // 模拟服务层返回
        when(alertService.getOverview(any(), any(), any(), any())).thenReturn(overview);

        // 执行测试
        mockMvc.perform(get("/api/v1/alert/statistics/overview")
                .param("tourismName", "测试景区")
                .param("deviceCode", "TEST001")
                .param("startTime", "2023-05-01 00:00:00")
                .param("endTime", "2023-05-31 23:59:59")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.totalCount").value(100))
                .andExpect(jsonPath("$.data.pendingCount").value(30))
                .andExpect(jsonPath("$.data.processedCount").value(70))
                .andExpect(jsonPath("$.data.processRate").value(70.0));
    }

    /**
     * 测试获取告警时段分布
     */
    @Test
    public void testGetTimeDistribution() throws Exception {
        // 准备测试数据
        Map<String, Object> timeDistribution = new HashMap<>();
        timeDistribution.put("data", new Object[]{});

        // 模拟服务层返回
        when(alertService.getTimeDistribution(any(), any(), any(), any())).thenReturn(timeDistribution);

        // 执行测试
        mockMvc.perform(get("/api/v1/alert/statistics/time-distribution")
                .param("tourismName", "测试景区")
                .param("deviceCode", "TEST001")
                .param("startTime", "2023-05-01 00:00:00")
                .param("endTime", "2023-05-31 23:59:59")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    /**
     * 测试获取告警类型分布
     */
    @Test
    public void testGetTypeDistribution() throws Exception {
        // 准备测试数据
        Map<String, Object> typeDistribution = new HashMap<>();
        typeDistribution.put("data", new Object[]{});

        // 模拟服务层返回
        when(alertService.getTypeDistribution(any(), any(), any(), any())).thenReturn(typeDistribution);

        // 执行测试
        mockMvc.perform(get("/api/v1/alert/statistics/type-distribution")
                .param("tourismName", "测试景区")
                .param("deviceCode", "TEST001")
                .param("startTime", "2023-05-01 00:00:00")
                .param("endTime", "2023-05-31 23:59:59")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    /**
     * 测试获取告警级别分布
     */
    @Test
    public void testGetLevelDistribution() throws Exception {
        // 准备测试数据
        Map<String, Object> levelDistribution = new HashMap<>();
        levelDistribution.put("data", new Object[]{});

        // 模拟服务层返回
        when(alertService.getLevelDistribution(any(), any(), any(), any())).thenReturn(levelDistribution);

        // 执行测试
        mockMvc.perform(get("/api/v1/alert/statistics/level-distribution")
                .param("tourismName", "测试景区")
                .param("deviceCode", "TEST001")
                .param("startTime", "2023-05-01 00:00:00")
                .param("endTime", "2023-05-31 23:59:59")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    /**
     * 测试获取告警趋势
     */
    @Test
    public void testGetTrend() throws Exception {
        // 准备测试数据
        Map<String, Object> trend = new HashMap<>();
        trend.put("data", new Object[]{});

        // 模拟服务层返回
        when(alertService.getTrend(any(), any(), any(), any())).thenReturn(trend);

        // 执行测试
        mockMvc.perform(get("/api/v1/alert/statistics/trend")
                .param("tourismName", "测试景区")
                .param("deviceCode", "TEST001")
                .param("startTime", "2023-05-01 00:00:00")
                .param("endTime", "2023-05-31 23:59:59")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    /**
     * 测试获取告警设备分布
     */
    @Test
    public void testGetDeviceDistribution() throws Exception {
        // 准备测试数据
        Map<String, Object> deviceDistribution = new HashMap<>();
        deviceDistribution.put("data", new Object[]{});

        // 模拟服务层返回
        when(alertService.getDeviceDistribution(any(), any(), any())).thenReturn(deviceDistribution);

        // 执行测试
        mockMvc.perform(get("/api/v1/alert/statistics/device-distribution")
                .param("tourismName", "测试景区")
                .param("startTime", "2023-05-01 00:00:00")
                .param("endTime", "2023-05-31 23:59:59")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    /**
     * 测试获取告警景区分布
     */
    @Test
    public void testGetTourismDistribution() throws Exception {
        // 准备测试数据
        Map<String, Object> tourismDistribution = new HashMap<>();
        tourismDistribution.put("data", new Object[]{});

        // 模拟服务层返回
        when(alertService.getTourismDistribution(any(), any())).thenReturn(tourismDistribution);

        // 执行测试
        mockMvc.perform(get("/api/v1/alert/statistics/tourism-distribution")
                .param("startTime", "2023-05-01 00:00:00")
                .param("endTime", "2023-05-31 23:59:59")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    /**
     * 测试获取所有统计数据
     */
    @Test
    public void testGetAllStatistics() throws Exception {
        // 准备测试数据
        Map<String, Object> allStatistics = new HashMap<>();
        allStatistics.put("overview", new HashMap<>());
        allStatistics.put("timeDistribution", new HashMap<>());
        allStatistics.put("typeDistribution", new HashMap<>());
        allStatistics.put("levelDistribution", new HashMap<>());
        allStatistics.put("trend", new HashMap<>());
        allStatistics.put("deviceDistribution", new HashMap<>());
        allStatistics.put("tourismDistribution", new HashMap<>());

        // 模拟服务层返回
        when(alertService.getOverview(any(), any(), any(), any())).thenReturn(new HashMap<>());
        when(alertService.getTimeDistribution(any(), any(), any(), any())).thenReturn(new HashMap<>());
        when(alertService.getTypeDistribution(any(), any(), any(), any())).thenReturn(new HashMap<>());
        when(alertService.getLevelDistribution(any(), any(), any(), any())).thenReturn(new HashMap<>());
        when(alertService.getTrend(any(), any(), any(), any())).thenReturn(new HashMap<>());
        when(alertService.getDeviceDistribution(any(), any(), any())).thenReturn(new HashMap<>());
        when(alertService.getTourismDistribution(any(), any())).thenReturn(new HashMap<>());

        // 执行测试
        mockMvc.perform(get("/api/v1/alert/statistics")
                .param("tourismName", "测试景区")
                .param("deviceCode", "TEST001")
                .param("startTime", "2023-05-01 00:00:00")
                .param("endTime", "2023-05-31 23:59:59")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }
} 