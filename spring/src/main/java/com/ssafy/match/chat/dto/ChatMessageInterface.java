package com.ssafy.match.chat.dto;


import java.time.LocalDateTime;

public interface ChatMessageInterface {
    String getMessage();
    String getSender();
    LocalDateTime getSent_time();
    String getNickname();
//    String getDbFile();
}
