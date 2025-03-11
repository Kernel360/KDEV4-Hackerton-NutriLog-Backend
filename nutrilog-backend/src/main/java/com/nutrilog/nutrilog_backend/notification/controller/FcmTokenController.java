package com.nutrilog.nutrilog_backend.notification.controller;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.notification.dto.FcmTokenRequest;
import com.nutrilog.nutrilog_backend.notification.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications/fcm-token")
@RequiredArgsConstructor
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping("")
    public ResponseEntity<?> registerFcmToken(
            @RequestBody FcmTokenRequest request,
            @AuthenticationPrincipal User userDetails
    ) {
        fcmTokenService.registerToken(userDetails, request.getToken());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteFcmToken(@AuthenticationPrincipal User userDetails) {
        fcmTokenService.deleteToken(userDetails);
        return ResponseEntity.noContent().build();
    }
}
