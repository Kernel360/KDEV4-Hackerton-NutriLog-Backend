package com.nutrilog.nutrilog_backend.common.filter;

import java.io.IOException;

import com.nutrilog.nutrilog_backend.auth.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    // HTTP 요청이 들어올 때마다 실행되는 필터
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        String token = getAccessToken(header);
        log.info("액세스 토큰 값: {}", token);
        if (token != null && tokenService.validateToken(token)) {
            // 유효한 토큰인 경우
            Authentication authentication = tokenService.getAuthenticationByToken(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 그 다음 요청 처리 체인을 이어서 진행
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String header) {
        log.info("[getAccessToken] 토큰 값 추출, {}", header);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
