package ru.springboot.resttest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsServiceImpl implements JmsService {
    private final JmsTemplate jmsTemplate;

    @Value("${activemq.queue}")
    private String queue;

    public JmsServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(queue, message, msg -> {
            msg.setStringProperty("type", "request");
            return msg;
        });
    }

    public String receiveMessage(String type) {
        return (String) jmsTemplate.receiveSelectedAndConvert(queue, type);
    }
}
