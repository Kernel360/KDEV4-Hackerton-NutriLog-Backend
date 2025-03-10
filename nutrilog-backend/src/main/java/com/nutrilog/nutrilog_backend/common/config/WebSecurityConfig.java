package com.nutrilog.nutrilog_backend.common.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
//    private final JwtProperties	jwtProperties;
//    private final UserDetailsService userDetailsService;

    // JWT PROVIDER 생성자 호출
//    private JwtProvider jwtProvider() {
//        return new JwtProvider(userDetailsService, jwtProperties);
//    }
    // TokenUtils 생성자 호출
//    private TokenUtils tokenUtils() {
//        return new TokenUtils(jwtProvider());
//    }

    // 암호화 빈
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 관리자 (AuthenticationManager) 설정
//    @Bean
//    AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
//        return new ProviderManager(authProvider);
//    }

    // HTTP 요청에 따른 보안 구성
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 경로 권한 설정
        http.authorizeHttpRequests(auth ->
                // 특정 URL 경로에 대해서는 인증 없이 접근 가능
                auth.requestMatchers(
                                new AntPathRequestMatcher("/api/auth/**")
                        ).permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/users/me/**")
                        ).authenticated() // 이 API는 인증이 필요함
                        // 그 밖의 다른 요청들은 인증을 통과한(로그인한) 사용자라면 모두 접근할 수 있도록 한다.
                        .anyRequest().authenticated()
        );

        // 무상태성 세션 관리
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // (토큰을 통해 검증할 수 있도록) 필터 추가 [jwtProvider 생성자를 호출해 매개변수로 등록]
//        http.addFilterAfter(new JwtAuthenticationFilter(jwtProvider()), UsernamePasswordAuthenticationFilter.class);

        // HTTP 기본 설정
        http.httpBasic(HttpBasicConfigurer::disable);
        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);
        // CORS 설정
        http.cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()));

        return http.getOrBuild();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            config.setAllowedOrigins(List.of("http://localhost:3000"));
            config.setAllowCredentials(true);
            return config;
        };
    }
}
