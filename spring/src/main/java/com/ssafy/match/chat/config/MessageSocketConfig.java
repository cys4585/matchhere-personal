package com.ssafy.match.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


@RequiredArgsConstructor
@Configuration
//@EnableWebSocket
@EnableWebSocketMessageBroker
public class MessageSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // TODO : CORS 설정 * 안먹히는 문제
        registry.addEndpoint("/socket/chat")
            .setAllowedOriginPatterns("*").withSockJS();
//            .setAllowedOrigins("https://localhost:8080", "https://i5d204.p.ssafy.io",
//                "http://localhost:8080").withSockJS();
//        registry.addEndpoint("/msgServer").setAllowedOrigins("*");
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}