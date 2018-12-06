package ru.springboot.resttest.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.springboot.resttest.model.JmsMessage;
import ru.springboot.resttest.service.JmsMessageService;

import java.util.List;

@Component
public class ScheduledTasks {
    private final JmsMessageService jmsMessageService;
    private final RestTemplate restTemplate;

    public ScheduledTasks(JmsMessageService jmsMessageService) {
        this.jmsMessageService = jmsMessageService;
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(cron = "${scheduler.cron}")
    public void sendJmsMessage() {
        List<JmsMessage> jmsMessages = this.jmsMessageService.findNotProcessed();
        jmsMessages.forEach(jmsMessage -> this.restTemplate.postForEntity("/send", jmsMessage, JmsMessage.class));
    }
}
