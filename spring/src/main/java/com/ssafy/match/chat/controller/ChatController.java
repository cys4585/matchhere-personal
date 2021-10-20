package com.ssafy.match.chat.controller;


import com.ssafy.match.chat.dao.ChatHistoryDao;
import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.chat.service.Receiver;
import com.ssafy.match.chat.service.Sender;
import com.ssafy.match.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class ChatController {
    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Autowired
    private ChatHistoryDao chatHistoryDao;

    private static String BOOT_TOPIC = "kafka-chat";

    private final TokenProvider tokenProvider;

    //// "url/app/message"로 들어오는 메시지를 "/topic/public"을 구독하고있는 사람들에게 송신
    @MessageMapping("/message")//@MessageMapping works for WebSocket protocol communication. This defines the URL mapping.
    @SendTo("/topic/public")//websocket subscribe topic& direct send
    public void sendMessage(ChatMessage message, @Header("Authorization") String token) throws Exception {
//        String userId = tokenProvider.getUserIdFromJwt(token);
        ConcurrentHashMap<String, String> concurrentHashMap = tokenProvider.getUserDataFromJwt(token);
        message.setSender(concurrentHashMap.get("userid"));
        message.setNickname(concurrentHashMap.get("nickname"));
        message.setSentTime(LocalDateTime.now());
        chatHistoryDao.save(message);
        sender.send(BOOT_TOPIC, message);
    }

    @RequestMapping("/history")
    public List<ChatMessage> getChattingHistory() throws Exception {
        return chatHistoryDao.get();
    }

    @MessageMapping("/file")
    @SendTo("/topic/chat")
    public ChatMessage sendFile(ChatMessage message) throws Exception {
        return new ChatMessage(message.getFileName(), message.getRawData(), message.getSender());
    }
}
