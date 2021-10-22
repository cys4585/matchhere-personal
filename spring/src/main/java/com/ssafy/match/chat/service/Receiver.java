package com.ssafy.match.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.match.chat.entity.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private SimpMessagingTemplate template;

    @KafkaListener( topics = "kafka-chat", groupId = "${kafka.group.id:${random.uuid}}")
    public void receive(ChatMessage message) throws Exception {
        LOGGER.info("message='{}'", message);
        HashMap<String, String> msg = new HashMap<>();
        msg.put("sent_time", message.getSent_time().format(DateTimeFormatter.ISO_DATE_TIME));
        msg.put("nickname", message.getNickname());
        msg.put("content", message.getContent());
        msg.put("sender_id", message.getSender_id());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(msg);

        this.template.convertAndSend("/room/" + message.getChatRoom().getId(), json);
    }

//    @KafkaListener(id = "main-listener", topics = "kafka-chat")
//    public void receive(ChatMessage message) throws Exception {
//        LOGGER.info("message='{}'", message);
//        HashMap<String, String> msg = new HashMap<>();
//        msg.put("sent_time", message.getSent_time().format(DateTimeFormatter.ISO_DATE_TIME));
//        msg.put("nickname", message.getNickname());
//        msg.put("content", message.getContent());
//        msg.put("sender_id", message.getSender_id());
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(msg);
//
//        this.template.convertAndSend("/topic/public", json);
//    }
}
