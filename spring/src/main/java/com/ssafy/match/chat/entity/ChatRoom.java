package com.ssafy.match.chat.entity;


import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "matching.chat_room")
public class ChatRoom {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "other_id")
    private Member other;

//    private int unReadCount;


    @Builder
    public ChatRoom(String id, Long user_id, String user_nickname, String user_pic, Long other_id, String other_nickname, String other_pic) {
        this.id = id;
        this.user_id = user_id;
        this.user_nickname = user_nickname;
        this.user_pic = user_pic;
        this.other_id = other_id;
        this.other_nickname = other_nickname;
        this.other_pic = other_pic;
    }
}
