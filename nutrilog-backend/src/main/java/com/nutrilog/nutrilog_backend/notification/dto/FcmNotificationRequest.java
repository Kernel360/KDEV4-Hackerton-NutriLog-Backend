package com.nutrilog.nutrilog_backend.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FcmNotificationRequest {
    private String token;
    private String title;
    private String body;
}
