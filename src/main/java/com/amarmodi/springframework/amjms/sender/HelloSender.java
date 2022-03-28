package com.amarmodi.springframework.amjms.sender;

import com.amarmodi.springframework.amjms.config.JmsConfig;
import com.amarmodi.springframework.amjms.model.HelloWorldMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE ,message);


    }

    @Scheduled(fixedRate = 2000)
    public void sendandReceiveMessage() throws JMSException {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        Message receivedMsd = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(mapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "com.amarmodi.springframework.amjms.model.HelloWorldMessage");
                    System.out.println("Sending hello message");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new JMSException("failed");
                }
                return helloMessage;
            }
        });


        System.out.println(receivedMsd.getBody(String.class));
    }

}
