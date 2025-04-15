package com.scenic.ai.service.impl;

import com.scenic.ai.domain.model.AlertDomain;
import com.scenic.ai.mapper.AlertHandlingRecordMapper;
import com.scenic.ai.model.AlertHandlingRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * AlertHandlingRecordService测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-test.xml"})
@Transactional
public class AlertHandlingRecordServiceImplTest {

    @Autowired
    private AlertHandlingRecordMapper alertHandlingRecordMapper;

    @Test
    public void testSave() {
        AlertHandlingRecord record = new AlertHandlingRecord();
        record.setAlertId(1L);
        record.setHandler("测试处理人");
        record.setHandleTime(LocalDateTime.now());
        record.setDescription("测试处理结果");

        int result = alertHandlingRecordMapper.insert(record);
        assertTrue(result > 0);
        assertNotNull(record.getId());
    }

    @Test
    public void testFindByAlertId() {
        // 先插入一条数据
        AlertHandlingRecord record = new AlertHandlingRecord();
        record.setAlertId(2L);
        record.setHandler("测试处理人2");
        record.setHandleTime(LocalDateTime.now());
        record.setDescription("测试处理结果2");
        alertHandlingRecordMapper.insert(record);

        // 查询并验证
        List<AlertHandlingRecord> records = alertHandlingRecordMapper.listByAlertId(2L);
        assertFalse(records.isEmpty());
        assertEquals(record.getAlertId(), records.get(0).getAlertId());
        assertEquals(record.getHandler(), records.get(0).getHandler());
    }
} 