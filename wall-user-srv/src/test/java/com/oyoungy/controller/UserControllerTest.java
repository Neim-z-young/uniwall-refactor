package com.oyoungy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyoungy.ddd.application.Service.UserService;
import com.oyoungy.ddd.application.assembler.UserAssembler;
import com.oyoungy.ddd.application.command.UserRegisterCommand;
import com.oyoungy.ddd.application.dto.UserDTO;
import com.oyoungy.service.SessionService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("ut")
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SessionService sessionService;

    @Autowired
    private ObjectMapper mapper;

    private UserAssembler userAssembler = UserAssembler.INSTANCE;

    @Test
    @WithMockUser
    void registerUser() throws Exception {
        String json = "{\"email\":\"123@qq.com\",\"nickname\":\"wall\",\"password\":\"1234\"}";
        UserRegisterCommand registerCommand = mapper.readValue(json, UserRegisterCommand.class);
        UserDTO userDTO = userAssembler.toUserDTO(userAssembler.toUser(registerCommand));

        userDTO.setId((long)1);
        userDTO.setGmtCreate(new Date());
        given(this.userService.registerUser(any(UserRegisterCommand.class))).willReturn(userDTO);

        MvcResult result = this.mvc
                .perform(post("/user/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerCommand)))
                .andExpect(status().is(200))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser
    void getUser() throws Exception {
        String json = "{\"email\":\"123@qq.com\",\"nickname\":\"wall\",\"password\":\"1234\"}";
        UserDTO userDTO = mapper.readValue(json, UserDTO.class);

        userDTO.setId((long)1);
        userDTO.setGmtCreate(new Date());
        given(this.userService.queryUser(anyLong())).willReturn(userDTO);

        MvcResult result = this.mvc.perform(
                get("/user/{0}", 1)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().is(200))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}