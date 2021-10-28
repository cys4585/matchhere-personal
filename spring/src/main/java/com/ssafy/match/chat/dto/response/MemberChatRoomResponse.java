package com.ssafy.match.chat.dto.response;


import com.ssafy.match.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberChatRoomResponse {
    private String room_id;
    private Long user_id;

    public static MemberChatRoomResponse of(ChatRoom chatRoom, Long user_id) {
        return MemberChatRoomResponse.builder()
                .room_id(chatRoom.getId())
                .user_id(user_id)
                .build();
    }

    @Builder
    public MemberChatRoomResponse(String room_id, Long user_id) {
        this.room_id = room_id;
        this.user_id = user_id;
    }
}
