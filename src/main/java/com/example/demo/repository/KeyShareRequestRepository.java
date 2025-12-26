package com.example.demo.repository;

import com.example.demo.model.KeyShareRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KeyShareRequestRepository extends JpaRepository<KeyShareRequest, Long> {
    List<KeyShareRequest> findBySharedWithId(Long guestId);
    List<KeyShareRequest> findBySharedById(Long guestId);
}