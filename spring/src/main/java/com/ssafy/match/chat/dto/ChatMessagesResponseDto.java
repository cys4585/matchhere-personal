package com.ssafy.match.chat.dto;

import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.common.dto.DetailPositionInterface;
import com.ssafy.match.file.dto.DBFileDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChatMessagesResponseDto {
    private List<DetailPositionInterface> dpositionList = new ArrayList<>();
//    private String message;
//    private String nickname;
//    private LocalDateTime sentTime;
//    private DBFileDto dbFile;

    public static ChatMessagesResponseDto of(ChatMessage chatMessage) {
        return ChatMessagesResponseDto.builder()
                .message(chatMessage.getMessage())
                .nickname(chatMessage.getNickname())
                .sentTime(chatMessage.getSent_time())
                .dbFile(DBFileDto.of(chatMessage.getDbFile()))
                .build();
    }

    @Builder
    public ChatMessagesResponseDto(String message, String nickname, LocalDateTime sentTime, DBFileDto dbFile) {
        this.message = message;
        this.nickname = nickname;
        this.sentTime = sentTime;
        this.dbFile = dbFile;
    }
}
