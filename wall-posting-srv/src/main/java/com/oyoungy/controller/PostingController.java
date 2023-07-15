package com.oyoungy.controller;

import com.oyoungy.response.ResultDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posting")
public class PostingController {

    @GetMapping("/")
    public ResultDTO<String> helloWorld(){
        return ResultDTO.success("hello world !!");
    }
}
