package com.nutrilog.nutrilog_backend.notification.service;

import com.nutrilog.nutrilog_backend.common.entities.User;

public interface FcmTokenService {
    public void registerToken(User loginUser, String token);
    public void deleteToken(User loginUser);
}
