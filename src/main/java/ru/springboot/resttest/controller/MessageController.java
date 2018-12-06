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

@RestController
public class MessageController {
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
    private final JmsService jmsService;
    private final JmsMessageService jmsMessageService;

    public MessageController(JmsService jmsService, JmsMessageService jmsMessageService) {
        this.jmsService = jmsService;
        this.jmsMessageService = jmsMessageService;
    }

    @PostMapping(value = "/send")
    public void sendMessage(@RequestBody JmsMessage jmsMessage) {
        try {
            this.jmsService.sendMessage(jmsMessage.getMessage());
            String receiveMessage = this.jmsService.receiveMessage("type = '" + jmsMessage.getMessage() + "'");
            jmsMessage.setProcessed(jmsMessage.getMessage().equals(receiveMessage));
        } catch (JmsException e) {
            jmsMessage.setProcessed(false);
        } finally {
            JmsMessage newMessage = this.jmsMessageService.save(jmsMessage);
            LOG.info("Saved message " + newMessage);
        }
    }
}
