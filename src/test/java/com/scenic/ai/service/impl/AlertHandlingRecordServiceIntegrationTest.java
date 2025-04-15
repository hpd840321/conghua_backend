package com.scenic.ai.service.impl;

import com.scenic.ai.model.AlertHandlingRecord;
import com.scenic.ai.service.AlertHandlingRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AlertHandlingRecordService集成测试
 * 
 * @author scenic
 */
@SpringJUnitConfig
@ContextConfiguration(locations = {
    "classpath:spring/spring-context-test.xml"
})
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"})
@Transactional
class AlertHandlingRecordServiceIntegrationTest {
    
    @Autowired
    private AlertHandlingRecordService alertHandlingRecordService;
    
    @Test
    void createRecord_Success() {
        // 准备测试数据
        Long alertId = 103L;
        String handler = "集成测试人员";
        String description = "集成测试处理说明";
        
        // 执行测试
        assertDoesNotThrow(() -> alertHandlingRecordService.createRecord(alertId, handler, description));
        
        // 验证结果
        List<AlertHandlingRecord> records = alertHandlingRecordService.getHandlingRecords(alertId);
        assertNotNull(records);
        assertFalse(records.isEmpty());
        
        AlertHandlingRecord record = records.get(0);
        assertEquals(alertId, record.getAlertId());
        assertEquals(handler, record.getHandler());
        assertEquals(description, record.getDescription());
        assertNotNull(record.getHandleTime());
        assertNotNull(record.getCreateTime());
    }
    
    @Test
    void getHandlingRecords_Success() {
        // 准备测试数据
        Long alertId = 100L;
        
        // 执行测试
        List<AlertHandlingRecord> records = alertHandlingRecordService.getHandlingRecords(alertId);
        
        // 验证结果
        assertNotNull(records);
        assertFalse(records.isEmpty());
        assertEquals(1, records.size());
        
        AlertHandlingRecord record = records.get(0);
        assertEquals(alertId, record.getAlertId());
        assertEquals("测试人员1", record.getHandler());
        assertEquals("测试处理说明1", record.getDescription());
    }
    
    @Test
    void getById_Success() {
        // 准备测试数据
        Long alertId = 100L;
        List<AlertHandlingRecord> records = alertHandlingRecordService.getHandlingRecords(alertId);
        assertFalse(records.isEmpty());
        Long recordId = records.get(0).getId();
        
        // 执行测试
        AlertHandlingRecord record = alertHandlingRecordService.getById(recordId);
        
        // 验证结果
        assertNotNull(record);
        assertEquals(alertId, record.getAlertId());
        assertEquals("测试人员1", record.getHandler());
        assertEquals("测试处理说明1", record.getDescription());
    }
    
    @Test
    void updateById_Success() {
        // 准备测试数据
        Long alertId = 100L;
        List<AlertHandlingRecord> records = alertHandlingRecordService.getHandlingRecords(alertId);
        assertFalse(records.isEmpty());
        AlertHandlingRecord record = records.get(0);
        
        // 修改数据
        String newDescription = "更新的处理说明";
        record.setDescription(newDescription);
        
        // 执行测试
        boolean result = alertHandlingRecordService.updateById(record);
        
        // 验证结果
        assertTrue(result);
        AlertHandlingRecord updatedRecord = alertHandlingRecordService.getById(record.getId());
        assertEquals(newDescription, updatedRecord.getDescription());
        assertNotNull(updatedRecord.getUpdateTime());
    }
    
    @Test
    void removeById_Success() {
        // 准备测试数据
        Long alertId = 100L;
        List<AlertHandlingRecord> records = alertHandlingRecordService.getHandlingRecords(alertId);
        assertFalse(records.isEmpty());
        Long recordId = records.get(0).getId();
        
        // 执行测试
        boolean result = alertHandlingRecordService.removeById(recordId);
        
        // 验证结果
        assertTrue(result);
        List<AlertHandlingRecord> remainingRecords = alertHandlingRecordService.getHandlingRecords(alertId);
        assertTrue(remainingRecords.isEmpty());
    }
    
    @Test
    void save_Success() {
        // 准备测试数据
        AlertHandlingRecord record = new AlertHandlingRecord();
        record.setAlertId(104L);
        record.setHandler("新建测试人员");
        record.setDescription("新建测试处理说明");
        
        // 执行测试
        boolean result = alertHandlingRecordService.save(record);
        
        // 验证结果
        assertTrue(result);
        assertNotNull(record.getId());
        
        AlertHandlingRecord savedRecord = alertHandlingRecordService.getById(record.getId());
        assertNotNull(savedRecord);
        assertEquals(record.getAlertId(), savedRecord.getAlertId());
        assertEquals(record.getHandler(), savedRecord.getHandler());
        assertEquals(record.getDescription(), savedRecord.getDescription());
        assertNotNull(savedRecord.getCreateTime());
        assertNotNull(savedRecord.getHandleTime());
    }
} 