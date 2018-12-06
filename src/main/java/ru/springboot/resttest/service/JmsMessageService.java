package ru.springboot.resttest.service;

import ru.springboot.resttest.model.JmsMessage;

import java.util.List;

public interface JmsMessageService {
    void save(JmsMessage jmsMessage);
    List<JmsMessage> findNotProcessed();
}
