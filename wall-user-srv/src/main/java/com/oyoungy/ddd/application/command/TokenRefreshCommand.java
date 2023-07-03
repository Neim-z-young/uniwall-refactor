package com.oyoungy.ddd.application.command;

import lombok.Data;

@Data
public class TokenRefreshCommand {
    private String refreshToken;
}
