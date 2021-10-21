package com.ssafy.match.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatMessagesResponseDto {
    private List<ChatMessageInterface> chatList = new ArrayList<>();

    public static ChatMessagesResponseDto of(List<ChatMessageInterface> chatMessageInterfaces) {
        return ChatMessagesResponseDto.builder()
                .chatMessageInterfaces(chatMessageInterfaces)
                .build();
    }

    @Builder
    public ChatMessagesResponseDto(List<ChatMessageInterface> chatMessageInterfaces) {
        this.chatList = chatMessageInterfaces;
    }
}
