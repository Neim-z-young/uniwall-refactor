package com.oyoungy.controller;

import com.oyoungy.ddd.application.query.PermissionQuery;
import com.oyoungy.response.ResultDTO;
import com.oyoungy.service.SessionService;
import com.oyoungy.tool.JwtTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private JwtTool jwtTool;

    @PostMapping
    ResultDTO<Void> check(@Validated @RequestBody PermissionQuery permissionQuery){
        String token = permissionQuery.getToken();
        if (jwtTool.validateAccess(token)) {
            Long userId = jwtTool.getUserIdFromToken(token);
            String role = jwtTool.getRoleFromToken(token);
            log.debug("TOKEN: " + token + " userId: " + userId + " role: " + role);
            if(jwtTool.getJwtConf().getAdminRole().equals(role)){
                return ResultDTO.success(null);
            }else{
                return ResultDTO.success(null);
            }
        }

        return ResultDTO.failed("token无效");
    }
}
