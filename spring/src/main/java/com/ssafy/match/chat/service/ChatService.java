package com.ssafy.match.chat.service;


import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final TokenProvider tokenProvider;

    @Transactional
    public void sendMessage(ChatMessage message, String token) {
        ConcurrentHashMap<String, String> concurrentHashMap = tokenProvider.getUserDataFromJwt(token);
        message.setSender(concurrentHashMap.get("userid"));
        message.setNickname(concurrentHashMap.get("nickname"));
        message.setSentTime(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public ChatMessageDto getHistory() {

    }
}
