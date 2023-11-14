package com.oyoungy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyoungy.ddd.application.assembler.UserAssembler;
import com.oyoungy.ddd.application.command.UserRegisterCommand;
import com.oyoungy.mock.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("ut")
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private UserAssembler userAssembler = UserAssembler.INSTANCE;

    @Test
    void registerUser() throws Exception {
        String json = "{\"email\":\"123@qq.com\",\"nickname\":\"wall\",\"password\":\"1234\"}";
        UserRegisterCommand registerCommand = mapper.readValue(json, UserRegisterCommand.class);

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
    void getUser() throws Exception {
        MvcResult result = this.mvc.perform(
                get("/user/{0}", 1)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().is(200))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockCustomUser
    void getUserInfo() throws Exception {
        MvcResult result = this.mvc.perform(
                get("/user/info/{0}", 1)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().is(200))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
