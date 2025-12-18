package com.example.demo.service;

import com.example.demo.model.KeyShareRequest;
import java.util.List;

public interface KeyShareRequestService {
    KeyShareRequest createShareRequest(KeyShareRequest request);
    KeyShareRequest updateStatus(long requestId, String status);
    KeyShareRequest getShareRequestById(long id);
    List<KeyShareRequest> getRequestsSharedBy(long guestId);
    List<KeyShareRequest> getRequestsSharedWith(long guestId);
}