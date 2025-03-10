package com.nutrilog.nutrilog_backend.auth.service;

import com.nutrilog.nutrilog_backend.auth.dto.LoginResponse;
import com.nutrilog.nutrilog_backend.auth.dto.OAuthUserInfo;
import com.nutrilog.nutrilog_backend.common.entities.UserType;

public interface AuthService {
    public OAuthUserInfo getOAuthUserInfo(String code, UserType provider);
    public LoginResponse login(String socialKey, UserType provider, String nickname);
}
