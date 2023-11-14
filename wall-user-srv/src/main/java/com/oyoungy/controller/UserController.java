package com.oyoungy.controller;

import com.oyoungy.ddd.application.Service.UserService;
import com.oyoungy.ddd.application.command.TokenRefreshCommand;
import com.oyoungy.ddd.application.command.UserLoginCommand;
import com.oyoungy.ddd.application.dto.TokenDTO;
import com.oyoungy.ddd.application.dto.UserDTO;
import com.oyoungy.ddd.application.command.UserRegisterCommand;
import com.oyoungy.ddd.application.dto.UserDetailDTO;
import com.oyoungy.ddd.application.dto.ValidateUserDTO;
import com.oyoungy.exceptions.WallBaseException;
import com.oyoungy.exceptions.WallNotFoundException;
import com.oyoungy.response.ResultDTO;
import com.oyoungy.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/register")
    ResultDTO<UserDTO> registerUser(@Validated @RequestBody UserRegisterCommand userRegisterCommand){
        return ResultDTO.success(userService.registerUser(userRegisterCommand));
    }

    @GetMapping("/{id}")
    ResultDTO<UserDTO> findUser(@PathVariable long id) throws WallNotFoundException {
        return ResultDTO.success(userService.queryUser(id));
    }

    @GetMapping("/info/{id}")
    ResultDTO<UserDetailDTO> findUserInfo(@PathVariable long id) throws WallNotFoundException {
        return ResultDTO.success(userService.queryUserDetail(id));
    }

    @PostMapping("/login")
    ResultDTO<TokenDTO> login(@RequestBody UserLoginCommand userLoginCommand) throws WallBaseException {
        return ResultDTO.success(userService.login(userLoginCommand));
    }

    @PostMapping("/validate")
    ResultDTO<ValidateUserDTO> validate() throws WallBaseException {
        return ResultDTO.success(sessionService.getCurrentUser());
    }

    @PostMapping("/refresh")
    ResultDTO<TokenDTO> refreshToken(@RequestBody TokenRefreshCommand refreshCommand){
        return ResultDTO.success(userService.refresh(refreshCommand));
    }
}
