package com.ssafy.match.chat.controller;


import com.ssafy.match.chat.dto.ChatMessageResponseDto;
import com.ssafy.match.chat.dto.ChatMessagesResponseDto;
import com.ssafy.match.chat.dto.request.ChatMessageRequestDto;
import com.ssafy.match.chat.dto.response.ChatRoomResponseDto;
import com.ssafy.match.chat.dto.response.ChatRoomsResponseDto;
import com.ssafy.match.chat.dto.response.MemberChatRoomResponse;
import com.ssafy.match.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/user/{id}")
    @SendTo("/room/{id}")
    public void sendMessage(ChatMessageRequestDto chatMessageRequestDto, @Header("Authorization") String token, @DestinationVariable Long id) throws Exception {
        chatService.sendMessage(chatMessageRequestDto, token, id);
    }

    @GetMapping("/chatroom/{id}")
    public ResponseEntity<List<ChatMessageResponseDto>> getChattingHistory(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(chatService.getHistory(id));
    }

    @GetMapping("/chatroom")
    public ResponseEntity<List<ChatRoomResponseDto>> getChattingRooms() throws Exception {
        return ResponseEntity.ok(chatService.getChattingRooms());
    }

    @GetMapping("/member/{email}")
    public ResponseEntity<MemberChatRoomResponse> getMemberChat(@PathVariable("email") String email) throws Exception {
        return ResponseEntity.ok(chatService.getChatroomId(email));
    }

//    @MessageMapping("/file")
//    @SendTo("/topic/chat")
//    public ChatMessage sendFile(ChatMessage message) throws Exception {
//        return new ChatMessage(message.getFileName(), message.getRawData(), message.getSender());
//    }
}
