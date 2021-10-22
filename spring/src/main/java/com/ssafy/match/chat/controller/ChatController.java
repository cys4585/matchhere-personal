package com.ssafy.match.chat.controller;


import com.ssafy.match.chat.dao.ChatHistoryDao;
import com.ssafy.match.chat.dto.ChatMessagesResponseDto;
import com.ssafy.match.chat.dto.request.ChatMessageRequestDto;
import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.chat.service.ChatService;
import com.ssafy.match.chat.service.Receiver;
import com.ssafy.match.chat.service.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class ChatController {
    private final ChatService chatService;

    //// "url/app/message"로 들어오는 메시지를 "/topic/public"을 구독하고있는 사람들에게 송신
    @MessageMapping("/user/{id}")//@MessageMapping works for WebSocket protocol communication. This defines the URL mapping.
    @SendTo("/room/{id}")//websocket subscribe topic& direct send
    public void sendMessage(ChatMessageRequestDto chatMessageRequestDto, @Header("Authorization") String token, @DestinationVariable Long id) throws Exception {
        chatService.sendMessage(chatMessageRequestDto, token, id);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<ChatMessagesResponseDto> getChattingHistory(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(chatService.getHistory(id));
    }

//    @MessageMapping("/file")
//    @SendTo("/topic/chat")
//    public ChatMessage sendFile(ChatMessage message) throws Exception {
//        return new ChatMessage(message.getFileName(), message.getRawData(), message.getSender());
//    }
}
