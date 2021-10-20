package com.ssafy.match.chat.entity;

import com.ssafy.match.file.entity.DBFile;
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

    private Long message_id;
    private String room_id;
    private LocalDateTime sent_time;
    private String nickname;
//    private Boolean is_read;

    private DBFile dbFile;
//    private String fileName;
//    private String rawData;

    public ChatMessage() {
    }

    public ChatMessage(String message, String sender, String roomId, LocalDateTime sentTime, String nickname) {
        this.message = message;
        this.sender = sender;
        this.roomId = roomId;
        this.sentTime = sentTime;
        this.nickname = nickname;
    }

    //    public ChatMessage(String message, String sender) {
//        this.sender = sender;
//        this.message = message;
//    }

//    public ChatMessage(String fileName, String rawData, String sender) {
//        this.fileName = fileName;
//        this.rawData = rawData;
//        this.sender = sender;
//    }

//    public ChatMessage(String message) {
//        this.message = message;
//    }
}
