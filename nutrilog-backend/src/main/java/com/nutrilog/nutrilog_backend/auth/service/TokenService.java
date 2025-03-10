package com.nutrilog.nutrilog_backend.auth.service;

import com.nutrilog.nutrilog_backend.common.entities.User;

public interface TokenService {
    public String generateAccessToken(User user);
}

