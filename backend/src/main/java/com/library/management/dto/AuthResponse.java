package com.library.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String userType;
    private Long userId;
    private String name;
    private String email;
}
