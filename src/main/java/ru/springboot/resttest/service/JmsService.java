package ru.springboot.resttest.service;

public interface JmsService {
    void sendMessage(String message);
    String receiveMessage(String type);
}
