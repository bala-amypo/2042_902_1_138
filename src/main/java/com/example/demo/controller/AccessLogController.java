package com.example.demo.controller;

import com.example.demo.model.AccessLog;
import com.example.demo.service.AccessLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
@Tag(name = "Access Logs")
public class AccessLogController {

    private final AccessLogService accessLogService;

    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @PostMapping
    @Operation(summary = "Create access log")
    public ResponseEntity<AccessLog> createLog(@RequestBody AccessLog log) {
        return ResponseEntity.ok(accessLogService.createLog(log));
    }

    @GetMapping("/key/{keyId}")
    @Operation(summary = "Get logs for digital key")
    public ResponseEntity<List<AccessLog>> getLogsForKey(@Parameter(description = "Key ID") @PathVariable Long keyId) {
        return ResponseEntity.ok(accessLogService.getLogsForKey(keyId));
    }

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get logs for guest")
    public ResponseEntity<List<AccessLog>> getLogsForGuest(@Parameter(description = "Guest ID") @PathVariable Long guestId) {
        return ResponseEntity.ok(accessLogService.getLogsForGuest(guestId));
    }
}



AuthController.java
package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Guest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final GuestService guestService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(GuestService guestService, JwtTokenProvider jwtTokenProvider, 
                         PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.guestService = guestService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new guest")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        Guest guest = new Guest();
        guest.setEmail(request.getEmail());
        guest.setPassword(request.getPassword());
        guest.setFullName(request.getFullName());
        guest.setPhoneNumber(request.getPhoneNumber());
        guest.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER");
        
        Guest created = guestService.createGuest(guest);
        return ResponseEntity.ok(new ApiResponse(true, "Guest registered successfully", created));
    }

    @PostMapping("/login")
    @Operation(summary = "Login guest")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        String token = jwtTokenProvider.generateToken(authentication);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", request.getEmail());
        
        return ResponseEntity.ok(new ApiResponse(true, "Login successful", response));
    }
}




DigitalKeyController.java
package com.example.demo.controller;

import com.example.demo.model.DigitalKey;
import com.example.demo.service.DigitalKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/digital-keys")
@Tag(name = "Digital Keys")
public class DigitalKeyController {

    private final DigitalKeyService digitalKeyService;

    public DigitalKeyController(DigitalKeyService digitalKeyService) {
        this.digitalKeyService = digitalKeyService;
    }

    @PostMapping("/generate/{bookingId}")
    @Operation(summary = "Generate digital key for booking")
    public ResponseEntity<DigitalKey> generateKey(@Parameter(description = "Booking ID") @PathVariable Long bookingId) {
        return ResponseEntity.ok(digitalKeyService.generateKey(bookingId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get digital key by ID")
    public ResponseEntity<DigitalKey> getKey(@Parameter(description = "Key ID") @PathVariable Long id) {
        return ResponseEntity.ok(digitalKeyService.getKeyById(id));
    }

    @GetMapping("/booking/{bookingId}")
    @Operation(summary = "Get active key for booking")
    public ResponseEntity<DigitalKey> getActiveKeyForBooking(@Parameter(description = "Booking ID") @PathVariable Long bookingId) {
        return ResponseEntity.ok(digitalKeyService.getActiveKeyForBooking(bookingId));
    }

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get keys for guest")
    public ResponseEntity<List<DigitalKey>> getKeysForGuest(@Parameter(description = "Guest ID") @PathVariable Long guestId) {
        return ResponseEntity.ok(digitalKeyService.getKeysForGuest(guestId));
    }
}



GeustController.java
package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest Management")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    @Operation(summary = "Create new guest")
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.createGuest(guest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get guest by ID")
    public ResponseEntity<Guest> getGuest(@Parameter(description = "Guest ID") @PathVariable Long id) {
        return ResponseEntity.ok(guestService.getGuestById(id));
    }

    @GetMapping
    @Operation(summary = "Get all guests")
    public ResponseEntity<List<Guest>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update guest")
    public ResponseEntity<Guest> updateGuest(@Parameter(description = "Guest ID") @PathVariable Long id, 
                                           @RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.updateGuest(id, guest));
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate guest")
    public ResponseEntity<Void> deactivateGuest(@Parameter(description = "Guest ID") @PathVariable Long id) {
        guestService.deactivateGuest(id);
        return ResponseEntity.ok().build();
    }
}



KeyShareRequestController.java
package com.example.demo.controller;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/key-share")
@Tag(name = "Key Sharing")
public class KeyShareRequestController {

    private final KeyShareRequestService keyShareRequestService;

    public KeyShareRequestController(KeyShareRequestService keyShareRequestService) {
        this.keyShareRequestService = keyShareRequestService;
    }

    @PostMapping
    @Operation(summary = "Create key share request")
    public ResponseEntity<KeyShareRequest> createShareRequest(@RequestBody KeyShareRequest request) {
        return ResponseEntity.ok(keyShareRequestService.createShareRequest(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get share request by ID")
    public ResponseEntity<KeyShareRequest> getShareRequest(@Parameter(description = "Request ID") @PathVariable Long id) {
        return ResponseEntity.ok(keyShareRequestService.getShareRequestById(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update share request status")
    public ResponseEntity<KeyShareRequest> updateStatus(@Parameter(description = "Request ID") @PathVariable Long id, 
                                                       @RequestParam String status) {
        return ResponseEntity.ok(keyShareRequestService.updateStatus(id, status));
    }

    @GetMapping("/shared-by/{guestId}")
    @Operation(summary = "Get requests shared by guest")
    public ResponseEntity<List<KeyShareRequest>> getRequestsSharedBy(@Parameter(description = "Guest ID") @PathVariable Long guestId) {
        return ResponseEntity.ok(keyShareRequestService.getRequestsSharedBy(guestId));
    }

    @GetMapping("/shared-with/{guestId}")
    @Operation(summary = "Get requests shared with guest")
    public ResponseEntity<List<KeyShareRequest>> getRequestsSharedWith(@Parameter(description = "Guest ID") @PathVariable Long guestId) {
        return ResponseEntity.ok(keyShareRequestService.getRequestsSharedWith(guestId));
    }
}



RoomBookingController.java
package com.example.demo.controller;

import com.example.demo.model.RoomBooking;
import com.example.demo.service.RoomBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Room Bookings")
public class RoomBookingController {

    private final RoomBookingService roomBookingService;

    public RoomBookingController(RoomBookingService roomBookingService) {
        this.roomBookingService = roomBookingService;
    }

    @PostMapping
    @Operation(summary = "Create new booking")
    public ResponseEntity<RoomBooking> createBooking(@RequestBody RoomBooking booking) {
        return ResponseEntity.ok(roomBookingService.createBooking(booking));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<RoomBooking> getBooking(@Parameter(description = "Booking ID") @PathVariable Long id) {
        return ResponseEntity.ok(roomBookingService.getBookingById(id));
    }

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get bookings for guest")
    public ResponseEntity<List<RoomBooking>> getBookingsForGuest(@Parameter(description = "Guest ID") @PathVariable Long guestId) {
        return ResponseEntity.ok(roomBookingService.getBookingsForGuest(guestId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update booking")
    public ResponseEntity<RoomBooking> updateBooking(@Parameter(description = "Booking ID") @PathVariable Long id, 
                                                   @RequestBody RoomBooking booking) {
        return ResponseEntity.ok(roomBookingService.updateBooking(id, booking));
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate booking")
    public ResponseEntity<Void> deactivateBooking(@Parameter(description = "Booking ID") @PathVariable Long id) {
        roomBookingService.deactivateBooking(id);
        return ResponseEntity.ok().build();
    }
}