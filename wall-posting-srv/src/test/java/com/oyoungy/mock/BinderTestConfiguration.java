package com.oyoungy.mock;


import com.oyoungy.ddd.application.event.CategoryApprovedEvent;
import com.oyoungy.ddd.application.event.PostingApprovedEvent;
import com.oyoungy.exceptions.WallNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.function.Consumer;

@EnableAutoConfiguration
@Slf4j
@ActiveProfiles("ut")
public class BinderTestConfiguration {
    @Bean
    public Consumer<CategoryApprovedEvent> approvedCategory(){
        return event -> {
            log.info("received event:{}", event);
        };
    }

    @Bean
    public Consumer<PostingApprovedEvent> approvedPosting(){
        return event -> {
            log.info("received event:{}", event);
        };
    }
}
