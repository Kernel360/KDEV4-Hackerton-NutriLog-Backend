package com.nutrilog.nutrilog_backend.notification.controller;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.notification.dto.FcmNotificationRequest;
import com.nutrilog.nutrilog_backend.notification.dto.FcmTokenRequest;
import com.nutrilog.nutrilog_backend.notification.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final FcmService fcmService;

    @PostMapping("/fcm-token")
    public ResponseEntity<?> registerFcmToken(
            @RequestBody FcmTokenRequest request,
            @AuthenticationPrincipal User userDetails
    ) {
        fcmService.registerToken(userDetails, request.getToken());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/fcm-token")
    public ResponseEntity<?> deleteFcmToken(@AuthenticationPrincipal User userDetails) {
        fcmService.deleteToken(userDetails);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendPushNotification(@RequestBody FcmNotificationRequest request) {
        fcmService.sendPushNotification(request.getToken(), request.getTitle(), request.getBody());
        return ResponseEntity.ok("푸시 알림 전송 완료");
    }
}
