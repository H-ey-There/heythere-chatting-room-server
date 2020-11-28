//package com.demo.chatdemo.config.kafka;
//
//import com.demo.chatdemo.chat.ChatMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//class ChatProducer {
//    private static final Logger logger = LoggerFactory.getLogger(ChatProducer.class);
//    static final String DEFAULT_CHATTING_TOPIC = "chat";
//
//    @Autowired
//    private KafkaTemplate<String, ChatMessage> kafkaTemplate;
//
//    public void sendMessage(ChatMessage message) {
//        logger.info(String.format("#### -> Producing message -> %s", message.getMessage()));
//        this.kafkaTemplate.send(DEFAULT_CHATTING_TOPIC, message.getRoomId(), message);
//    }
//}
