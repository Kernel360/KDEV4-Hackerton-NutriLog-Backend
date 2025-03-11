package com.nutrilog.nutrilog_backend.auth.service;

import com.nutrilog.nutrilog_backend.common.entities.User;
import org.springframework.security.core.Authentication;

public interface TokenService {
    public String generateAccessToken(User user);
    public boolean validateToken(String token);
    public Authentication getAuthenticationByToken(String token);
}

