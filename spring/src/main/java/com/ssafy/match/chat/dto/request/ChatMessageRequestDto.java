package com.ssafy.match.chat.dto.request;


import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestDto {
    private String content;

    public ChatMessage toChatMessage(ConcurrentHashMap<String, String> concurrentHashMap, ChatRoom inner_chatroom) {
        return ChatMessage.builder()
                .content(content)
                .sender_id(concurrentHashMap.get("userid"))
                .nickname(concurrentHashMap.get("nickname"))
                .sent_time(LocalDateTime.now())
                .chatRoom(inner_chatroom)
                .build();
    }
}
