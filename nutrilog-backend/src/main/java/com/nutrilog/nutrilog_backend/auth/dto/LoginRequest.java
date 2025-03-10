package com.nutrilog.nutrilog_backend.auth.dto;

import com.nutrilog.nutrilog_backend.common.entities.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String code;
    private UserType provider;
}
