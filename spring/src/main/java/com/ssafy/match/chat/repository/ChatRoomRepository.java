package com.ssafy.match.chat.repository;


import com.ssafy.match.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String>  {
}
