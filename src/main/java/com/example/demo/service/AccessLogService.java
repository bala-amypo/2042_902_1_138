package com.example.demo.service;

import com.example.demo.model.AccessLog;
import java.util.List;

public interface AccessLogService {

    // Used by tests
    AccessLog createLog(AccessLog log);

    List<AccessLog> getLogsForGuest(Long guestId);

    List<AccessLog> getLogsForKey(Long keyId);

    // Used by controller
    AccessLog logAccess(AccessLog log);
}
