package com.example.demo.service.impl;

import com.example.demo.model.AccessLog;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.service.AccessLogService;

import java.sql.Timestamp;
import java.util.List;

public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository repo;

    public AccessLogServiceImpl(AccessLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public AccessLog createLog(AccessLog log) {
        if (log.getAccessTime().after(new Timestamp(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("future access not allowed");
        }
        log.setResult("SUCCESS");
        return repo.save(log);
    }

    @Override
    public List<AccessLog> getLogsForKey(Long keyId) {
        return repo.findByDigitalKeyId(keyId);
    }

    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {
        return repo.findByGuestId(guestId);
    }
}
