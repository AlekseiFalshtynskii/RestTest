package ru.springboot.resttest.service;

import org.springframework.stereotype.Service;
import ru.springboot.resttest.model.JmsMessage;
import ru.springboot.resttest.repository.JmsMessageRepository;

import java.util.List;

@Service
public class JmsMessageServiceImpl implements JmsMessageService {
    private final JmsMessageRepository repository;

    public JmsMessageServiceImpl(JmsMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public JmsMessage save(JmsMessage jmsMessage) {
        return this.repository.insert(jmsMessage);
    }

    @Override
    public List<JmsMessage> findNotProcessed() {
        return this.repository.findByProcessed(false);
    }
}
