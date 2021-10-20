package com.ssafy.match.chat.entity;

import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "matching.chat_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long message_id;

    private String message;
    private String sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    private LocalDateTime sent_time;
    private String nickname;
//    private Boolean is_read;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "db_file")
    private DBFile dbFile;
//    private String fileName;
//    private String rawData;

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
