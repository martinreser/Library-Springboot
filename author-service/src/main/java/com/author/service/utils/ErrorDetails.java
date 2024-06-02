package com.author.service.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class ErrorDetails {
    private String timestamp;
    private String message;
    private String details;

    public ErrorDetails (LocalDateTime timestamp, String message, String details){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.timestamp = timestamp.truncatedTo(java.time.temporal.ChronoUnit.MINUTES).format(formatter).toString();
        this.message = message;
        this.details = details;
    }
}
