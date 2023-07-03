package com.oyoungy.ddd.application.dto;

import lombok.Data;

@Data
public class TokenDTO {
    private Long userId;
    private String refreshToken;
    private String token;
}
