package com.oyoungy;

import com.oyoungy.util.PropertyUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
//        PropertyUtils.init();
        SpringApplication.run(UserApplication.class, args);
    }
}
