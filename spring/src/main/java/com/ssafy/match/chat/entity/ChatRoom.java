package com.ssafy.match.chat.entity;


import com.ssafy.match.file.entity.DBFile;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "matching.chat_room")
public class ChatRoom {
    @Id
    private String id;

    private Long user_id;
    private String user_nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pic_id")
    private DBFile user_pic;

    private Long other_id;
    private String other_nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "other_pic_id")
    private DBFile other_pic;

//    private int unReadCount;


    @Builder
    public ChatRoom(String id, Long user_id, String user_nickname, DBFile user_pic, Long other_id, String other_nickname, DBFile other_pic) {
        this.id = id;
        this.user_id = user_id;
        this.user_nickname = user_nickname;
        this.user_pic = user_pic;
        this.other_id = other_id;
        this.other_nickname = other_nickname;
        this.other_pic = other_pic;
    }
}
