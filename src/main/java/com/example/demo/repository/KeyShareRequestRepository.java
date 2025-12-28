package com.example.demo.repository;

import com.example.demo.model.KeyShareRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// KeyShareRequestRepository
public interface KeyShareRequestRepository extends JpaRepository<KeyShareRequest,Long> {
    List<KeyShareRequest> findBySharedById(Long id);
    List<KeyShareRequest> findBySharedWithId(Long id);
}
