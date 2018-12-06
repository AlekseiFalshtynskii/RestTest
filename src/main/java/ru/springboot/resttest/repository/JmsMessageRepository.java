package ru.springboot.resttest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.springboot.resttest.model.JmsMessage;

import java.util.List;

public interface JmsMessageRepository extends MongoRepository<JmsMessage, Long> {
    List<JmsMessage> findByProcessed(boolean processed);
}
