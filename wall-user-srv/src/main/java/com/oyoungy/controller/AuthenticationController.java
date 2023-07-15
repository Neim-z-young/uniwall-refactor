package com.oyoungy.controller;

import com.oyoungy.ddd.application.query.PermissionQuery;
import com.oyoungy.response.ResultDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @PostMapping
    ResultDTO<Void> check(@Validated @RequestBody PermissionQuery permissionQuery){
        return ResultDTO.success(null);
    }
}
