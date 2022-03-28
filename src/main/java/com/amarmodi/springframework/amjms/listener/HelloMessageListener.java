package com.amarmodi.springframework.amjms.listener;

import com.amarmodi.springframework.amjms.config.JmsConfig;
import com.amarmodi.springframework.amjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders messageHeaders, Message message){
//        System.out.println("I got a message!");
//        System.out.println(helloWorldMessage);
    }


    @SneakyThrows
    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders messageHeaders, Message message){

        HelloWorldMessage sendMessage = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World")
                .build();
        System.out.println("I got a message!");
        System.out.println(helloWorldMessage);
        jmsTemplate.convertAndSend((Destination) message.getJMSReplyTo(),sendMessage);
    }
}
