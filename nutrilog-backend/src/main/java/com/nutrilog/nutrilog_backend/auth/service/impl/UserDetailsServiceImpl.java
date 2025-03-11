package com.nutrilog.nutrilog_backend.auth.service.impl;

import com.nutrilog.nutrilog_backend.auth.repository.UserRepository;
import com.nutrilog.nutrilog_backend.common.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String socialKey) throws UsernameNotFoundException {
        log.info("[loadUserByUsername] 사용자 소셜키로 조회. socialKey: {}", socialKey);
        User user = userRepository.findBySocialKey(socialKey).orElseThrow(() -> new IllegalArgumentException("없는 이메일"));

        return (UserDetails) user;
    }
}