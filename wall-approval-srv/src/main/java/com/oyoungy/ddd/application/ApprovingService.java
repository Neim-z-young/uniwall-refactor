package com.oyoungy.ddd.application;

import com.oyoungy.ddd.application.event.CategoryApprovingEvent;
import com.oyoungy.ddd.application.event.PostingApprovingEvent;
import com.oyoungy.exceptions.WallNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class ApprovingService {

    @Bean
    public Consumer<CategoryApprovingEvent> approvingCategory(){
        return event -> {
            log.info("received event:{}", event);
            //todo create approval bill
        };
    }

    @Bean
    public Consumer<PostingApprovingEvent> approvingPosting(){
        return event -> {
            log.info("received event:{}", event);
        };
    }

}
