package com.ssafy.match.chat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessage {
    private String message;
    private String user;
    private Long timeStamp;

    private String fileName;
    private String rawData;

    public ChatMessage() {
    }

    public ChatMessage(String message, String user) {
        this.user = user;
        this.message = message;
    }

    public ChatMessage(String fileName, String rawData, String user) {

        this.fileName = fileName;
        this.rawData = rawData;
        this.user = user;
    }

    public ChatMessage(String message) {
        this.message = message;
    }
}
