package com.ssafy.match.chat.service;


import com.ssafy.match.chat.dao.ChatHistoryDao;
import com.ssafy.match.chat.dto.ChatMessageInterface;
import com.ssafy.match.chat.dto.ChatMessagesResponseDto;
import com.ssafy.match.chat.dto.request.ChatMessageRequestDto;
import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.chat.entity.ChatRoom;
import com.ssafy.match.chat.repository.ChatMessageRepository;
import com.ssafy.match.chat.repository.ChatRoomRepository;
import com.ssafy.match.jwt.TokenProvider;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ChatService {
    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Autowired
    private ChatHistoryDao chatHistoryDao;

    private final TokenProvider tokenProvider;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    private static String BOOT_TOPIC = "kafka-chat";

    @Transactional
    public void sendMessage(ChatMessageRequestDto chatMessageRequestDto, String token, Long id) {
        ConcurrentHashMap<String, String> concurrentHashMap = tokenProvider.getUserDataFromJwt(token);
        Member other = memberRepository.findById(id).orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다!"));
        String roomid = getRoomId(Long.parseLong(concurrentHashMap.get("userid")), other.getId());
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(roomid);
        ChatMessage message;
        if (chatRoom.isEmpty()) {
            ChatRoom inner_chatroom = createChatRoom(roomid, Long.parseLong(concurrentHashMap.get("userid")), concurrentHashMap.get("nickname"), other.getId(), other.getNickname());
            chatRoomRepository.save(inner_chatroom);
            message = chatMessageRequestDto.toChatMessage(concurrentHashMap, inner_chatroom);
        } else {
            message = chatMessageRequestDto.toChatMessage(concurrentHashMap, chatRoom.get());
        }
        chatMessageRepository.save(message);
//        chatHistoryDao.save(message);
        sender.send(BOOT_TOPIC, message);
    }

    @Transactional
    public ChatRoom createChatRoom(String roomid, long user_id, String user_nickname, Long other_id, String other_nickname) {
        return ChatRoom.builder()
                .id(roomid)
                .user_id(user_id)
                .user_nickname(user_nickname)
                .other_id(other_id)
                .other_nickname(other_nickname)
                .build();
    }

//    @Transactional
//    public ChatRoom createChatRoom(String roomid, Long userid, String nickname) {
//        return ChatRoom.builder()
//                .id(roomid)
//                .other_id()
//                .user_id(userid)
//                .build();
//    }

    @Transactional(readOnly = true)
    public ChatMessagesResponseDto getHistory(Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("잘못된 토큰입니다."));
        String roomid = getRoomId(member.getId(), id);
        ChatRoom chatRoom = chatRoomRepository.findById(roomid).orElseThrow(() -> new NullPointerException("존재하지 않는 채팅방입니다!"));
        List<ChatMessageInterface> chatMessages = chatMessageRepository.findAllByRoom(chatRoom);
        ChatMessagesResponseDto chatMessagesResponseDto = ChatMessagesResponseDto.of(chatMessages);
        return chatMessagesResponseDto;
    }

    private String getRoomId(Long myid, Long id) {
        String roomid;
        if (myid > id){
            roomid = Long.toString(id) + '-' + Long.toString(myid);
        } else {
            roomid = Long.toString(myid) + '-' + Long.toString(id);
        }
        return roomid;
    }
}