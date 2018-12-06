package ru.springboot.resttest.service;

import ru.springboot.resttest.model.JmsMessage;

import java.util.List;

public interface JmsMessageService {
    JmsMessage save(JmsMessage jmsMessage);
    List<JmsMessage> findNotProcessed();
}
