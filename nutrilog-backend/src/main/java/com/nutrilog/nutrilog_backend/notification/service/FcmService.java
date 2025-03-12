package com.nutrilog.nutrilog_backend.notification.service;

import com.nutrilog.nutrilog_backend.common.entities.User;

public interface FcmService {
    public void registerToken(User loginUser, String token);
    public void deleteToken(User loginUser);
    public void sendPushNotification(String token, String title, String body);
}
