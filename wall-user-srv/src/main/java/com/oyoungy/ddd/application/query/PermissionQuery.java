package com.oyoungy.ddd.application.query;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class PermissionQuery {
    @NotNull
    private String method;

    @NotNull
    private Long user;

    @NotNull
    private String url;
}
