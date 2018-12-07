package ru.springboot.resttest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.springboot.resttest.model.JmsMessage;
import ru.springboot.resttest.service.JmsMessageService;
import ru.springboot.resttest.service.JmsService;

import java.util.Random;

@RestController
public class MessageController {
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
    private final JmsService jmsService;
    private final JmsMessageService jmsMessageService;
    private final Random random;

    public MessageController(JmsService jmsService, JmsMessageService jmsMessageService) {
        this.jmsService = jmsService;
        this.jmsMessageService = jmsMessageService;
        this.random = new Random();
    }

    @PostMapping(value = "/send")
    public void sendMessage(@RequestBody JmsMessage jmsMessage) {
        try {
            if (this.random.nextBoolean()) {
                throw new org.springframework.jms.IllegalStateException(new javax.jms.IllegalStateException("Send error"));
            }
            this.jmsService.sendMessage(jmsMessage.getMessage());
            String receiveMessage = this.jmsService.receiveMessage("type = '" + jmsMessage.getMessage() + "'");
            jmsMessage.setProcessed(jmsMessage.getMessage().equals(receiveMessage));
        } catch (JmsException e) {
            LOG.error(e.getMessage());
            jmsMessage.setProcessed(false);
        } finally {
            JmsMessage newMessage = this.jmsMessageService.save(jmsMessage);
            LOG.info("Saved message " + newMessage);
        }
    }
}
