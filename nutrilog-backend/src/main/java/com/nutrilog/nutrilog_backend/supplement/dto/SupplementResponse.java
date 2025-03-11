package com.nutrilog.nutrilog_backend.supplement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplementResponse {
    private Long id;
    private String name;
}