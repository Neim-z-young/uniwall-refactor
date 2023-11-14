package com.oyoungy.ddd.event;

import com.oyoungy.ddd.application.event.CategoryApprovedEvent;
import com.oyoungy.ddd.application.event.CategoryApprovingEvent;
import com.oyoungy.ddd.application.event.PostingApprovedEvent;
import com.oyoungy.ddd.application.event.PostingApprovingEvent;
import com.oyoungy.ddd.domain.vo.CategoryId;
import com.oyoungy.enums.OperationEnum;
import com.oyoungy.enums.StateEnum;
import com.oyoungy.mock.BinderTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;


import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class BinderTest {

    @Test
    public void testMultipleFunctions() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(
                        BinderTestConfiguration.class))
                .run("--spring.profiles.active=ut",
                        "--spring.cloud.function.definition=approvedCategory;approvedPosting",
                        "--spring.cloud.stream.bindings.approvedCategory-in-0.destination=categoryIn",
                        "--spring.cloud.stream.bindings.approvedPosting-in-0.destination=postingIn")) {

            InputDestination inputDestination = context.getBean(InputDestination.class);

            CategoryApprovedEvent categoryApprovedEvent = new CategoryApprovedEvent();
            categoryApprovedEvent.setApprovingMsg("待删除");
            categoryApprovedEvent.setCategory("test_category");
            categoryApprovedEvent.setOperation(OperationEnum.DELETE.getMsg());
            categoryApprovedEvent.setState(StateEnum.APPROVING.getMsg());
            categoryApprovedEvent.setCategoryId(0L);
            categoryApprovedEvent.setApprovingUserId(null);
            Message<CategoryApprovedEvent> inputMessage = MessageBuilder.withPayload(categoryApprovedEvent).build();

            inputDestination.send(inputMessage, "categoryIn");


            PostingApprovedEvent postingApprovedEvent = new PostingApprovedEvent();
            postingApprovedEvent.setApprovingMsg("待创建");
            postingApprovedEvent.setPostingId(BigInteger.ONE);
            postingApprovedEvent.setCategoryId(0L);
            postingApprovedEvent.setOperation(OperationEnum.CREATE.getMsg());
            postingApprovedEvent.setState(StateEnum.APPROVING.getMsg());
            postingApprovedEvent.setApprovingUserId(null);
            Message<PostingApprovedEvent> inputMessage2 = MessageBuilder.withPayload(postingApprovedEvent).build();
            inputDestination.send(inputMessage, "postingIn");


        }
    }
}
