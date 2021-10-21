package com.ssafy.match.chat.dto;


import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.file.dto.DBFileDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponseDto {
    private String content;
    private String nickname;
    private LocalDateTime sentTime;
    private DBFileDto dbFile;

    public static ChatMessageResponseDto of(ChatMessage chatMessage) {
        return ChatMessageResponseDto.builder()
                .content(chatMessage.getContent())
                .nickname(chatMessage.getNickname())
                .sentTime(chatMessage.getSent_time())
                .dbFile(DBFileDto.of(chatMessage.getDbFile()))
                .build();
    }

    @Builder
    public ChatMessageResponseDto(String content, String nickname, LocalDateTime sentTime, DBFileDto dbFile) {
        this.content = content;
        this.nickname = nickname;
        this.sentTime = sentTime;
        this.dbFile = dbFile;
    }
}
