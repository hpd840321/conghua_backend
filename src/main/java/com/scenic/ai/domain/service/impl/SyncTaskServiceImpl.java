package com.scenic.ai.domain.service.impl;

import com.scenic.ai.domain.mapper.SyncTaskMapper;
import com.scenic.ai.domain.model.SyncTask;
import com.scenic.ai.domain.service.SyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SyncTaskServiceImpl implements SyncTaskService {

    @Autowired
    private SyncTaskMapper syncTaskMapper;

    @Override
    @Transactional
    public void createTask(SyncTask task) {
        task.setStatus("PENDING");
        task.setRetryCount(0);
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        syncTaskMapper.insert(task);
    }

    @Override
    @Transactional
    public void updateTask(SyncTask task) {
        task.setUpdateTime(LocalDateTime.now());
        syncTaskMapper.update(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        syncTaskMapper.deleteById(id);
    }

    @Override
    public SyncTask getTaskById(Long id) {
        return syncTaskMapper.selectById(id);
    }

    @Override
    public List<SyncTask> getPendingTasks() {
        return syncTaskMapper.selectPendingTasks();
    }

    @Override
    public List<SyncTask> getRunningTasks() {
        return syncTaskMapper.selectRunningTasks();
    }

    @Override
    @Transactional
    public void updateTaskStatus(Long id, String status, String message) {
        syncTaskMapper.updateStatus(id, status, message);
    }

    @Override
    public List<SyncTask> getTimeoutTasks(Integer timeoutMinutes) {
        return syncTaskMapper.selectTimeoutTasks(timeoutMinutes);
    }

    @Override
    public List<SyncTask> searchTasks(String type, String status,
                                    LocalDateTime startTime, LocalDateTime endTime) {
        return syncTaskMapper.selectByCondition(type, status, startTime, endTime);
    }

    @Override
    @Transactional
    public void retryTask(Long id) {
        SyncTask task = syncTaskMapper.selectById(id);
        if (task != null && task.getRetryCount() < task.getMaxRetries()) {
            task.setStatus("PENDING");
            task.setRetryCount(task.getRetryCount() + 1);
            task.setNextRetryTime(LocalDateTime.now());
            task.setUpdateTime(LocalDateTime.now());
            syncTaskMapper.update(task);
        }
    }

    @Override
    @Transactional
    public void cancelTask(Long id) {
        syncTaskMapper.updateStatus(id, "CANCELLED", "Task cancelled by user");
    }
} 