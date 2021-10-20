package com.ssafy.match.chat.entity;


import com.ssafy.match.file.entity.DBFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "matching.chat_room")
public class ChatRoom {
    @Id
    private String id;

    private String user_id;
    private String user_nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pic_id")
    private DBFile user_pic;

    private String other_id;
    private String other_nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pic_id")
    private DBFile other_pic;

//    private
//    private String masterEmail;
//    private String masterName;
//    private String masterPic;
//
//    private int unReadCount;
}
