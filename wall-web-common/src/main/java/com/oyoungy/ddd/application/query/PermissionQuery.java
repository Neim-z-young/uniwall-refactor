package com.oyoungy.ddd.application.query;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PermissionQuery {
    @NotNull
    private String method;

    @NotNull
    private String token;

    @NotNull
    private String url;
}
