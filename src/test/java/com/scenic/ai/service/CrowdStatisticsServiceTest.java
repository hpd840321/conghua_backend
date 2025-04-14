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
    private CrowdStatisticsService crowdStatisticsService;

    @Test
    public void testPage() {
        // 测试正常分页
        Page<CrowdStatistics> page = new Page<>(1, 10);
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        IPage<CrowdStatistics> result = crowdStatisticsService.page(page, tourismName, deviceCode, startTime, endTime);

        assertNotNull(result);
        assertTrue(result.getTotal() >= 0);
        assertNotNull(result.getRecords());
        
        // 测试空参数
        result = crowdStatisticsService.page(page, null, null, null, null);
        assertNotNull(result);
        
        // 测试无效时间范围
        result = crowdStatisticsService.page(page, tourismName, deviceCode, endTime, startTime);
        assertTrue(result.getRecords().isEmpty());
    }

    // ... existing code ...

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

    // ... existing code ...

    @Test
    public void testGetHighDensityAreas() {
        String tourismName = "测试景区1";
        String deviceCode = "DEV001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal threshold = new BigDecimal("0.8");

        List<Map<String, Object>> result = crowdStatisticsService.getHighDensityAreas(
            tourismName, deviceCode, startTime, endTime, threshold);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        result.forEach(stat -> {
            assertTrue(((BigDecimal) stat.get("density")).compareTo(threshold) >= 0);
            assertEquals(tourismName, stat.get("tourismName"));
            LocalDateTime recordTime = (LocalDateTime) stat.get("recordTime");
            assertTrue(recordTime.isAfter(startTime) || recordTime.equals(startTime));
            assertTrue(recordTime.isBefore(endTime) || recordTime.equals(endTime));
        });
        
        // 测试极限阈值
        result = crowdStatisticsService.getHighDensityAreas(
            tourismName, deviceCode, startTime, endTime, new BigDecimal("1.0"));
        assertNotNull(result);
        
        // 测试无效阈值
        result = crowdStatisticsService.getHighDensityAreas(
            tourismName, deviceCode, startTime, endTime, new BigDecimal("-1.0"));
        assertTrue(result.isEmpty());
    }
}