package com.nutrilog.nutrilog_backend.auth.controller;

import com.nutrilog.nutrilog_backend.auth.dto.LoginRequest;
import com.nutrilog.nutrilog_backend.auth.dto.LoginResponse;
import com.nutrilog.nutrilog_backend.auth.dto.OAuthUserInfo;
import com.nutrilog.nutrilog_backend.auth.service.AuthService;
import com.nutrilog.nutrilog_backend.common.entities.UserType;
import com.nutrilog.nutrilog_backend.common.util.NicknameGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        logger.info("들어온 코드 값: {}", loginRequest.getCode());
        OAuthUserInfo oAuthUserInfo = authService.getOAuthUserInfo(loginRequest.getCode(), loginRequest.getProvider());
        LoginResponse response = authService.login(oAuthUserInfo.getSocialKey(), oAuthUserInfo.getProvider(), loginRequest.getCode());
        return response;
    }
}
