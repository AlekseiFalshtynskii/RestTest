package ru.springboot.resttest.jms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsClient {
    private final JmsTemplate jmsTemplate;

    @Value("${activemq.queue}")
    private String queue;

    public JmsClient(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "${activemq.queue}", containerFactory = "myFactory", selector = "type = 'request'")
    public void receiveMessage(String message) {
        jmsTemplate.convertAndSend(queue, message, msg -> {
            msg.setStringProperty("type", message);
            return msg;
        });
    }
}
