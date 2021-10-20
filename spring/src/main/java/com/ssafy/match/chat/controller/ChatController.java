package com.ssafy.match.chat.controller;


import com.ssafy.match.chat.dao.ChatHistoryDao;
import com.ssafy.match.chat.dto.ChatMessageResponseDto;
import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.chat.service.ChatService;
import com.ssafy.match.chat.service.Receiver;
import com.ssafy.match.chat.service.Sender;
import com.ssafy.match.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class ChatController {
    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Autowired
    private ChatHistoryDao chatHistoryDao;

    private static String BOOT_TOPIC = "kafka-chat";

    private final ChatService chatService;

    //// "url/app/message"로 들어오는 메시지를 "/topic/public"을 구독하고있는 사람들에게 송신
    @MessageMapping("/message")//@MessageMapping works for WebSocket protocol communication. This defines the URL mapping.
    @SendTo("/topic/public")//websocket subscribe topic& direct send
    public void sendMessage(ChatMessage message, @Header("Authorization") String token) throws Exception {
        chatService.sendMessage(message, token);
        chatHistoryDao.save(message);
        sender.send(BOOT_TOPIC, message);
    }

    @GetMapping("/{id}")
//    @RequestMapping("/history")
    public ResponseEntity<ChatMessageResponseDto> getChattingHistory(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(chatService.getHistory(id));
//        return chatHistoryDao.get();
    }

//    @MessageMapping("/file")
//    @SendTo("/topic/chat")
//    public ChatMessage sendFile(ChatMessage message) throws Exception {
//        return new ChatMessage(message.getFileName(), message.getRawData(), message.getSender());
//    }
}
