package ru.springboot.resttest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class JmsMessage {
    @Id
    private String id;
    private String message;
    private boolean processed;
}
