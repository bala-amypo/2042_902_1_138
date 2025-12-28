package com.example.demo.repository;

import com.example.demo.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// AccessLogRepository
public interface AccessLogRepository extends JpaRepository<AccessLog,Long> {
    List<AccessLog> findByGuestId(Long id);
    List<AccessLog> findByDigitalKeyId(Long id);
}
