package com.ssafy.match.chat.dto.response;


import com.ssafy.match.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberChatRoomResponse {
    private String room_id;

    public static MemberChatRoomResponse of(ChatRoom chatRoom) {
        return MemberChatRoomResponse.builder()
                .room_id(chatRoom.getId())
                .build();
    }

    @Builder
    public MemberChatRoomResponse(String room_id) {
        this.room_id = room_id;
    }
}
