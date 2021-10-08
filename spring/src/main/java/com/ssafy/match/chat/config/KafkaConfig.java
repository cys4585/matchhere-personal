//package com.ssafy.match.chat.config;
//
//
//import com.ssafy.match.chat.dto.ChatMessage;
//import java.util.HashMap;
//import java.util.Map;
//import org.apache.kafka.common.serialization.IntegerDeserializer;
//import org.apache.kafka.common.serialization.IntegerSerializer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//@EnableKafka
//@Configuration
//public class KafkaConfig {
//    //Sender config
//    @Bean
//    public ProducerFactory<String, ChatMessage> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs(), null, new JsonSerializer<ChatMessage>());
//    }
//
//    @Bean
//    public KafkaTemplate<String, ChatMessage> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public Map<String, Object> producerConfigs() {
//        Map<String, Object> ret = new HashMap<>();
//        ret.put("bootstrap.servers", "localhost:29092"); //kafka server ip & port
//        ret.put("key.serializer", StringSerializer.class);
//        ret.put("value.serializer", JsonSerializer.class); // Object json parser
//        ret.put("group.id", "test-group"); // chatting  group id
//        return ret;
//    }
//    //Receiver config
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, ChatMessage> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, ChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, ChatMessage> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), null, new JsonDeserializer<>(ChatMessage.class));
//    }
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> ret = new HashMap<>();
//        ret.put("bootstrap.servers", "localhost:29092");
//        ret.put("key.deserializer", StringDeserializer.class);
//        ret.put("value.deserializer", JsonDeserializer.class);
//        ret.put("group.id", "test-group");
//        return ret;
//    }
//}
