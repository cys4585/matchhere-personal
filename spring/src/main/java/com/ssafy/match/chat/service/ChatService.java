package com.ssafy.match.chat.service;


import com.ssafy.match.chat.dto.ChatMessageResponseDto;
import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.chat.entity.ChatRoom;
import com.ssafy.match.chat.repository.ChatMessageRepository;
import com.ssafy.match.chat.repository.ChatRoomRepository;
import com.ssafy.match.jwt.TokenProvider;
import com.ssafy.match.member.dto.response.CertificationResponseDto;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final TokenProvider tokenProvider;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void sendMessage(ChatMessage message, String token) {
        ConcurrentHashMap<String, String> concurrentHashMap = tokenProvider.getUserDataFromJwt(token);
        message.setSender(concurrentHashMap.get("userid"));
        message.setNickname(concurrentHashMap.get("nickname"));
        message.setSent_time(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public ChatMessageResponseDto getHistory(Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("잘못된 토큰입니다."));
        Long myid = member.getId();
        String roomid;
        if (myid > id){
            roomid = Long.toString(id) + '-' + Long.toString(member.getId());
        } else {
            roomid = Long.toString(member.getId()) + '-' + Long.toString(id);
        }
        ChatRoom chatRoom = chatRoomRepository.findById(roomid).orElseThrow(() -> new NullPointerException("존재하지 않는 채팅방입니다!"));
        ChatMessage chatMessage = chatMessageRepository.findAllByRoomId(chatRoom);

//        chatMessageRepository.findById().orElseThrow(() -> new NullPointerException("잘못된 토큰입니다."));;
    }
}
