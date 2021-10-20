package com.ssafy.match.chat.repository;


import com.ssafy.match.chat.dto.ChatMessageInterface;
import com.ssafy.match.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query(value = "select mm.message, mm.sent_time, mm.nickname from matching.message mm where mm.room_id = :room_id")
    List<ChatMessageInterface> findAllByRoomId(@Param("room_id") String room_id);
}
