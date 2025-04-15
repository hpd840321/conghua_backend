package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.CrowdStatistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class CrowdStatisticsServiceTest {

    @Autowired
    private ICrowdStatisticsService crowdStatisticsService;

    @Test
    public void testGetPage() {
        // 测试正常分页
        Integer pageNum = 1;
        Integer pageSize = 10;
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        IPage<CrowdStatistics> result = crowdStatisticsService.getPage(pageNum, pageSize, tourismName, deviceCode, startTime, endTime);

        assertNotNull(result);
        assertTrue(result.getTotal() >= 0);
        assertNotNull(result.getRecords());
        
        // 测试空参数
        result = crowdStatisticsService.getPage(pageNum, pageSize, null, null, null, null);
        assertNotNull(result);
        
        // 测试无效时间范围
        result = crowdStatisticsService.getPage(pageNum, pageSize, tourismName, deviceCode, endTime, startTime);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    public void testGetOverview() {
        String tourismName = "测试景区";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        Map<String, Object> result = crowdStatisticsService.getOverview(tourismName, startTime, endTime);

        assertNotNull(result);
        assertTrue(result.containsKey("totalCount"));
        assertTrue(result.containsKey("avgDensity"));
        assertTrue(result.containsKey("maxDensity"));
        assertTrue(result.containsKey("highDensityCount"));
        assertTrue(((Number) result.get("totalCount")).intValue() >= 0);
        assertTrue(((BigDecimal) result.get("avgDensity")).compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(((BigDecimal) result.get("maxDensity")).compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(((Number) result.get("highDensityCount")).intValue() >= 0);
    }

    @Test
    public void testGetDensityDistribution() {
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        List<Map<String, Object>> result = crowdStatisticsService.getDensityDistribution(
            deviceCode, tourismName, startTime, endTime);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        result.forEach(stat -> {
            assertTrue(((BigDecimal) stat.get("density")).compareTo(BigDecimal.ZERO) >= 0);
            assertEquals(tourismName, stat.get("tourismName"));
            LocalDateTime recordTime = (LocalDateTime) stat.get("recordTime");
            assertTrue(recordTime.isAfter(startTime) || recordTime.equals(startTime));
            assertTrue(recordTime.isBefore(endTime) || recordTime.equals(endTime));
        });
    }

    @Test
    public void testGetByDevice() {
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        List<CrowdStatistics> result = crowdStatisticsService.getByDevice(deviceCode, startTime, endTime);

        assertNotNull(result);
        result.forEach(stat -> {
            assertEquals(deviceCode, stat.getDeviceCode());
            assertTrue(stat.getRecordTime().isAfter(startTime) || stat.getRecordTime().equals(startTime));
            assertTrue(stat.getRecordTime().isBefore(endTime) || stat.getRecordTime().equals(endTime));
        });
    }

    @Test
    public void testGetByTourism() {
        String tourismName = "测试景区";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        List<CrowdStatistics> result = crowdStatisticsService.getByTourism(tourismName, startTime, endTime);

        assertNotNull(result);
        result.forEach(stat -> {
            assertEquals(tourismName, stat.getTourismName());
            assertTrue(stat.getRecordTime().isAfter(startTime) || stat.getRecordTime().equals(startTime));
            assertTrue(stat.getRecordTime().isBefore(endTime) || stat.getRecordTime().equals(endTime));
        });
    }

    @Test
    public void testGetHourDistribution() {
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        List<Map<String, Object>> result = crowdStatisticsService.getHourDistribution(
            deviceCode, tourismName, startTime, endTime);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        result.forEach(stat -> {
            assertTrue(((Number) stat.get("count")).intValue() >= 0);
            assertTrue(((Number) stat.get("hour")).intValue() >= 0);
            assertTrue(((Number) stat.get("hour")).intValue() < 24);
        });
    }

    @Test
    public void testGetTrend() {
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        List<Map<String, Object>> result = crowdStatisticsService.getTrend(
            deviceCode, tourismName, startTime, endTime);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        result.forEach(stat -> {
            assertTrue(((Number) stat.get("count")).intValue() >= 0);
            assertNotNull(stat.get("time"));
        });
    }
}