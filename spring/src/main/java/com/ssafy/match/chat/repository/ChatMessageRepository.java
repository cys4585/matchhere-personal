package com.ssafy.match.chat.repository;


import com.ssafy.match.chat.dto.ChatMessageInterface;
import com.ssafy.match.chat.entity.ChatMessage;
import com.ssafy.match.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query(value = "select mm.content, mm.sent_time, mm.nickname from matching.chat_message mm where mm.chatRoom = :chatRoom")
    List<ChatMessageInterface> findAllByRoomId(@Param("chatRoom") ChatRoom chatRoom);
}
