package com.scenic.ai.mapper;

import com.scenic.ai.model.Alert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * AlertMapper测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-test.xml"})
@Transactional
public class AlertMapperTest {

    @Autowired
    private AlertMapper alertMapper;

    @Test
    public void testInsert() {
        Alert alert = new Alert();
        alert.setDeviceCode("TEST001");
        alert.setDeviceName("测试设备");
        alert.setTourismName("测试景区");
        alert.setAlertType("CROWD_GATHERING");
        alert.setAlertLevel(1);
        alert.setAlertStatus(0);
        alert.setDescription("测试告警");
        alert.setImageUrl("http://test.com/image.jpg");
        alert.setRecordTime(LocalDateTime.now());

        int result = alertMapper.insert(alert);
        assertTrue(result > 0);
        assertNotNull(alert.getId());
    }

    @Test
    public void testFindById() {
        // 先插入一条数据
        Alert alert = new Alert();
        alert.setDeviceCode("TEST002");
        alert.setDeviceName("测试设备2");
        alert.setTourismName("测试景区2");
        alert.setAlertType("CROWD_GATHERING");
        alert.setAlertLevel(1);
        alert.setAlertStatus(0);
        alert.setDescription("测试告警2");
        alert.setImageUrl("http://test.com/image2.jpg");
        alert.setRecordTime(LocalDateTime.now());
        alertMapper.insert(alert);

        // 查询并验证
        Alert found = alertMapper.findById(alert.getId());
        assertNotNull(found);
        assertEquals(alert.getDeviceCode(), found.getDeviceCode());
        assertEquals(alert.getDeviceName(), found.getDeviceName());
        assertEquals(alert.getTourismName(), found.getTourismName());
    }

    @Test
    public void testSelectByDevice() {
        // 先插入一条数据
        Alert alert = new Alert();
        alert.setDeviceCode("TEST003");
        alert.setDeviceName("测试设备3");
        alert.setTourismName("测试景区3");
        alert.setAlertType("CROWD_GATHERING");
        alert.setAlertLevel(1);
        alert.setAlertStatus(0);
        alert.setDescription("测试告警3");
        alert.setImageUrl("http://test.com/image3.jpg");
        alert.setRecordTime(LocalDateTime.now());
        alertMapper.insert(alert);

        // 查询并验证
        List<Alert> alerts = alertMapper.selectByDevice("TEST003", 
            LocalDateTime.now().minusDays(1), 
            LocalDateTime.now());
        assertFalse(alerts.isEmpty());
        assertEquals(alert.getDeviceCode(), alerts.get(0).getDeviceCode());
    }

    @Test
    public void testGetTimeDistribution() {
        // 准备测试数据
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        // 执行测试
        List<Map<String, Object>> result = alertMapper.getTimeDistribution(
                tourismName, deviceCode, startTime, endTime);

        // 验证结果
        assertNotNull(result);
    }

    @Test
    public void testGetTypeDistribution() {
        // 准备测试数据
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        // 执行测试
        List<Map<String, Object>> result = alertMapper.getTypeDistribution(
                tourismName, deviceCode, startTime, endTime);

        // 验证结果
        assertNotNull(result);
    }

    @Test
    public void testGetLevelDistribution() {
        // 准备测试数据
        String tourismName = "测试景区";
        String deviceCode = "TEST001";
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        // 执行测试
        List<Map<String, Object>> result = alertMapper.getLevelDistribution(
                tourismName, deviceCode, startTime, endTime);

        // 验证结果
        assertNotNull(result);
    }

    @Test
    public void testCountUnhandledAlerts() {
        // 准备测试数据
        String tourismName = "测试景区";
        String deviceCode = "TEST001";

        // 执行测试
        Long count = alertMapper.countUnhandledAlerts(deviceCode, tourismName);

        // 验证结果
        assertNotNull(count);
        assertTrue(count >= 0);
    }

    @Test
    public void testBatchUpdateStatus() {
        // 准备测试数据
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        Integer status = 1;

        // 执行测试
        int result = alertMapper.batchUpdateStatus(ids, status);

        // 验证结果
        assertTrue(result >= 0);
    }

    @Test
    public void testDeleteHistoricalData() {
        // 准备测试数据
        LocalDateTime beforeTime = LocalDateTime.now().minusDays(30);

        // 执行测试
        int result = alertMapper.deleteHistoricalData(beforeTime);

        // 验证结果
        assertTrue(result >= 0);
    }

    @Test
    public void testInsertBatch() {
        // 准备测试数据
        List<Alert> alerts = new ArrayList<>();
        Alert alert1 = new Alert();
        alert1.setTourismName("测试景区1");
        alert1.setDeviceCode("TEST001");
        alert1.setAlertType("类型1");
        alert1.setAlertLevel(1);
        alert1.setDescription("测试内容1");
        alert1.setAlertStatus(0);
        alerts.add(alert1);

        Alert alert2 = new Alert();
        alert2.setTourismName("测试景区2");
        alert2.setDeviceCode("TEST002");
        alert2.setAlertType("类型2");
        alert2.setAlertLevel(2);
        alert2.setDescription("测试内容2");
        alert2.setAlertStatus(0);
        alerts.add(alert2);

        // 执行测试
        int result = alertMapper.insertBatch(alerts);

        // 验证结果
        assertTrue(result > 0);
    }
} 