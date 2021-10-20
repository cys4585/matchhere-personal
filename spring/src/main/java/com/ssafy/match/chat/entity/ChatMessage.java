package com.ssafy.match.chat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChatMessage {
    private String message;
    private String sender;

//    private Long timeStamp;
    private LocalDateTime sentTime;

    private String fileName;
    private String rawData;

    public ChatMessage() {
    }

    public ChatMessage(String message, String sender) {
        this.sender = sender;
        this.message = message;
    }

    public ChatMessage(String fileName, String rawData, String sender) {

        this.fileName = fileName;
        this.rawData = rawData;
        this.sender = sender;
    }

    public ChatMessage(String message) {
        this.message = message;
    }
}
