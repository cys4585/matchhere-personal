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



}
