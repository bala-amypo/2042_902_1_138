package com.example.demo.service;

import com.example.demo.model.KeyShareRequest;
import java.util.List;

public interface KeyShareRequestService {
    KeyShareRequest createShareRequest(KeyShareRequest req);
    void revokeShareRequest(Long id);
    List<KeyShareRequest> getRequestsSharedBy(Long id);
    List<KeyShareRequest> getRequestsSharedWith(Long id);
}
