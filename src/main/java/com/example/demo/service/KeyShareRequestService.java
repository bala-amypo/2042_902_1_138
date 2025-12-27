package com.example.demo.service;

import com.example.demo.model.KeyShareRequest;
import java.util.List;

public interface KeyShareRequestService {
    KeyShareRequest createShareRequest(KeyShareRequest request);
    List<KeyShareRequest> getRequestsSharedBy(Long guestId);
    List<KeyShareRequest> getRequestsSharedWith(Long guestId);
}
