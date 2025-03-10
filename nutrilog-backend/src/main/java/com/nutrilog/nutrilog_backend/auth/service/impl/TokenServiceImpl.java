package com.nutrilog.nutrilog_backend.auth.service.impl;

import com.nutrilog.nutrilog_backend.auth.dto.JwtProperties;
import com.nutrilog.nutrilog_backend.auth.service.TokenService;
import com.nutrilog.nutrilog_backend.common.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtProperties jwtProperties;

    // JWT Access 토큰 생성 메소드
    public String generateAccessToken(User user) {
        log.info("[generateAccessToken] 액세스 토큰을 생성합니다.");
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + jwtProperties.getAccessDuration());
        return makeToken(user, expiredDate);
    }

    // 토큰 생성 공통 메소드 (실제로 JWT 토큰 생성)
    private String makeToken(User user, Date expiredDate) {
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT") // JWT 타입 명시
                .setIssuer(jwtProperties.getIssuer()) // 발행자 정보 설정
                .setIssuedAt(new Date()) // 발행일시 설정
                .setExpiration(expiredDate) // 만료 시간 설정
                .setSubject(user.getNickname()) // 토큰 주제(사용자 닉네임)
                .claim("id", user.getId()) // 사용자 ID claim 설정
                .signWith(getSecretKey(), SignatureAlgorithm.HS256) // 비밀키와 해시 알고리즘 설정
                .compact(); // 최종적으로 토큰 압축하여 문자열 반환

        log.info("[makeToken] 완성된 토큰 : {}", token);
        return token;
    }

    // 비밀키 만드는 메소드
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }
}
