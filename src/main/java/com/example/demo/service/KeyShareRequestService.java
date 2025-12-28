package com.example.demo.service;

import com.example.demo.model.KeyShareRequest;
import java.util.List;

public interface KeyShareRequestService {

    // Used by tests
    KeyShareRequest createShareRequest(KeyShareRequest request);

    List<KeyShareRequest> getRequestsSharedBy(Long guestId);

    List<KeyShareRequest> getRequestsSharedWith(Long guestId);

    // Used by controller
    KeyShareRequest shareKey(KeyShareRequest request);
}
