package com.ssafy.match.chat.entity;

import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChatMessage {
    private String message;
    private String sender;

    private Long message_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    private LocalDateTime sent_time;
    private String nickname;
//    private Boolean is_read;

    private DBFile dbFile;
//    private String fileName;
//    private String rawData;

    public ChatMessage() {
    }

    public ChatMessage(String message, String sender, ChatRoom chatRoom, LocalDateTime sentTime, String nickname) {
        this.message = message;
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.sent_time = sentTime;
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
