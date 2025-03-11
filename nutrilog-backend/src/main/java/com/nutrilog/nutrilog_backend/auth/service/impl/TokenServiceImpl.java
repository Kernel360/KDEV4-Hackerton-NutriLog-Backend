package com.nutrilog.nutrilog_backend.auth.service.impl;

import com.nutrilog.nutrilog_backend.auth.dto.JwtProperties;
import com.nutrilog.nutrilog_backend.auth.service.TokenService;
import com.nutrilog.nutrilog_backend.common.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserDetailsService userDetailsService;
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
                .claim("socialKey", user.getSocialKey())
                .signWith(getSecretKey(), SignatureAlgorithm.HS256) // 비밀키와 해시 알고리즘 설정
                .compact(); // 최종적으로 토큰 압축하여 문자열 반환

        log.info("[makeToken] 완성된 토큰 : {}", token);
        return token;
    }

    // 비밀키 만드는 메소드
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }

    // 토큰이 유효한지 검증 메소드
    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 검증을 시작합니다.");
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey()) // 비밀키 설정
                    .build()
                    .parseClaimsJws(token); // 서명된 클레임을 파싱

            log.info("토큰 검증 통과");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("토큰 검증 실패");
        return false;
    }

    // 토큰에서 정보(Claim) 추출 메소드
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey()) // 비밀키 설정
                .build()
                .parseClaimsJws(token) // JWT 파싱 (유효성 검사 포함)
                .getBody(); // 클레임 정보 반환
    }


    // 토큰에서 인증 정보 반환하는 메소드
    public Authentication getAuthenticationByToken(String token) {
        log.info("[getAuthenticationByToken] 토큰 인증 정보 조회");
        String socialKey = getSocialKeyByToken(token);
        User user = (User) userDetailsService.loadUserByUsername(socialKey);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, token, null
        );
        return authentication;
    }

    // 토큰에서 사용자 이메일만 추출하는 메소드
    public String getSocialKeyByToken(String token) {
        log.info("[getSocialKeyByToken] 토큰 기반 회원 식별 정보 추출");
        Claims claims = getClaims(token);
        log.info("{}", claims.get("socialKey", String.class));
        String socialKey = claims.get("socialKey", String.class);
        return socialKey;
    }

}
