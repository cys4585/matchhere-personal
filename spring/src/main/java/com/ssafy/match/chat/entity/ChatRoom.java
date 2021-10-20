package com.ssafy.match.chat.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "matching.chat_room")
public class ChatRoom {
    @Id
    private String roomId;

    private String user1Id;
    private String user1Nickname;
    private String user1Pic;

    private String user2Id;
    private String user2Nickname;
    private String user2Pic;

//    private
//    private String masterEmail;
//    private String masterName;
//    private String masterPic;
//
//    private int unReadCount;
}
