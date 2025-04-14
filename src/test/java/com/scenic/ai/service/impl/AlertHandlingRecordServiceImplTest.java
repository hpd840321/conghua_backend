package com.scenic.ai.service.impl;

import com.scenic.ai.dao.AlertHandlingRecordMapper;
import com.scenic.ai.exception.BusinessException;
import com.scenic.ai.model.AlertHandlingRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * AlertHandlingRecordService单元测试
 * 
 * @author scenic
 */
@ExtendWith(MockitoExtension.class)
class AlertHandlingRecordServiceImplTest {
    
    @Mock
    private AlertHandlingRecordMapper alertHandlingRecordMapper;
    
    @InjectMocks
    private AlertHandlingRecordServiceImpl alertHandlingRecordService;
    
    private AlertHandlingRecord testRecord;
    private LocalDateTime now;
    
    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        testRecord = new AlertHandlingRecord();
        testRecord.setId(1L);
        testRecord.setAlertId(100L);
        testRecord.setHandler("测试人员");
        testRecord.setDescription("测试处理说明");
        testRecord.setHandleTime(now);
        testRecord.setCreateTime(now);
        testRecord.setUpdateTime(now);
    }
    
    @Test
    void createRecord_Success() {
        // 准备测试数据
        Long alertId = 100L;
        String handler = "测试人员";
        String description = "测试处理说明";
        
        // Mock保存操作
        when(alertHandlingRecordMapper.insert(any(AlertHandlingRecord.class))).thenReturn(1);
        
        // 执行测试
        assertDoesNotThrow(() -> alertHandlingRecordService.createRecord(alertId, handler, description));
        
        // 验证调用
        verify(alertHandlingRecordMapper, times(1)).insert(any(AlertHandlingRecord.class));
    }
    
    @Test
    void createRecord_WithNullAlertId_ThrowsException() {
        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> alertHandlingRecordService.createRecord(null, "测试人员", "测试处理说明"));
        assertEquals("告警ID不能为空", exception.getMessage());
        
        // 验证mapper未被调用
        verify(alertHandlingRecordMapper, never()).insert(any());
    }
    
    @Test
    void createRecord_WithEmptyHandler_ThrowsException() {
        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> alertHandlingRecordService.createRecord(100L, "", "测试处理说明"));
        assertEquals("处理人不能为空", exception.getMessage());
        
        // 验证mapper未被调用
        verify(alertHandlingRecordMapper, never()).insert(any());
    }
    
    @Test
    void getHandlingRecords_Success() {
        // 准备测试数据
        Long alertId = 100L;
        List<AlertHandlingRecord> expectedRecords = Arrays.asList(testRecord);
        
        // Mock查询操作
        when(alertHandlingRecordMapper.selectByAlertId(alertId)).thenReturn(expectedRecords);
        
        // 执行测试
        List<AlertHandlingRecord> actualRecords = alertHandlingRecordService.getHandlingRecords(alertId);
        
        // 验证结果
        assertNotNull(actualRecords);
        assertEquals(1, actualRecords.size());
        assertEquals(testRecord.getId(), actualRecords.get(0).getId());
        assertEquals(testRecord.getAlertId(), actualRecords.get(0).getAlertId());
        
        // 验证调用
        verify(alertHandlingRecordMapper, times(1)).selectByAlertId(alertId);
    }
    
    @Test
    void getHandlingRecords_WithNullAlertId_ThrowsException() {
        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> alertHandlingRecordService.getHandlingRecords(null));
        assertEquals("告警ID不能为空", exception.getMessage());
        
        // 验证mapper未被调用
        verify(alertHandlingRecordMapper, never()).selectByAlertId(any());
    }
    
    @Test
    void getById_Success() {
        // 准备测试数据
        Long id = 1L;
        
        // Mock查询操作
        when(alertHandlingRecordMapper.selectById(id)).thenReturn(testRecord);
        
        // 执行测试
        AlertHandlingRecord actualRecord = alertHandlingRecordService.getById(id);
        
        // 验证结果
        assertNotNull(actualRecord);
        assertEquals(testRecord.getId(), actualRecord.getId());
        assertEquals(testRecord.getAlertId(), actualRecord.getAlertId());
        
        // 验证调用
        verify(alertHandlingRecordMapper, times(1)).selectById(id);
    }
    
    @Test
    void getById_NotFound_ThrowsException() {
        // 准备测试数据
        Long id = 999L;
        
        // Mock查询操作
        when(alertHandlingRecordMapper.selectById(id)).thenReturn(null);
        
        // 执行测试并验证异常
        BusinessException exception = assertThrows(BusinessException.class,
                () -> alertHandlingRecordService.getById(id));
        assertEquals("处理记录不存在", exception.getMessage());
        
        // 验证调用
        verify(alertHandlingRecordMapper, times(1)).selectById(id);
    }
    
    @Test
    void updateById_Success() {
        // Mock更新操作
        when(alertHandlingRecordMapper.updateById(any(AlertHandlingRecord.class))).thenReturn(1);
        
        // 执行测试
        boolean result = alertHandlingRecordService.updateById(testRecord);
        
        // 验证结果
        assertTrue(result);
        
        // 验证调用
        verify(alertHandlingRecordMapper, times(1)).updateById(any(AlertHandlingRecord.class));
    }
    
    @Test
    void updateById_WithNullId_ThrowsException() {
        // 准备测试数据
        testRecord.setId(null);
        
        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> alertHandlingRecordService.updateById(testRecord));
        assertEquals("记录ID不能为空", exception.getMessage());
        
        // 验证mapper未被调用
        verify(alertHandlingRecordMapper, never()).updateById(any());
    }
    
    @Test
    void removeById_Success() {
        // Mock删除操作
        when(alertHandlingRecordMapper.deleteById(anyLong())).thenReturn(1);
        
        // 执行测试
        boolean result = alertHandlingRecordService.removeById(1L);
        
        // 验证结果
        assertTrue(result);
        
        // 验证调用
        verify(alertHandlingRecordMapper, times(1)).deleteById(anyLong());
    }
    
    @Test
    void removeById_WithNullId_ThrowsException() {
        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> alertHandlingRecordService.removeById(null));
        assertEquals("记录ID不能为空", exception.getMessage());
        
        // 验证mapper未被调用
        verify(alertHandlingRecordMapper, never()).deleteById(any());
    }
    
    @Test
    void save_Success() {
        // Mock保存操作
        when(alertHandlingRecordMapper.insert(any(AlertHandlingRecord.class))).thenReturn(1);
        
        // 执行测试
        boolean result = alertHandlingRecordService.save(testRecord);
        
        // 验证结果
        assertTrue(result);
        
        // 验证调用
        verify(alertHandlingRecordMapper, times(1)).insert(any(AlertHandlingRecord.class));
    }
    
    @Test
    void save_WithNullAlertId_ThrowsException() {
        // 准备测试数据
        testRecord.setAlertId(null);
        
        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> alertHandlingRecordService.save(testRecord));
        assertEquals("告警ID不能为空", exception.getMessage());
        
        // 验证mapper未被调用
        verify(alertHandlingRecordMapper, never()).insert(any());
    }
} 