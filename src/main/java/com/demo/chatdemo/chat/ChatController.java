package com.demo.chatdemo.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.KafkaMessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class ChatController {

    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
    private final SimpMessageSendingOperations messagingTemplate;
    private final RoomRepository roomRepository;
    private final UserSessionRepository userSessionRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message){
        kafkaTemplate.send("chatting", message.getRoomId(), message.message());
    }

    @KafkaListener(id = "main-listener", topics = "chatting")
    public void receive(ChatMessage message,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) throws Exception {

        if (MessageType.QUIT.equals(message.getType())) {
            Room room = roomRepository.findById(key)
                    .orElseThrow(Exception::new);
            roomRepository.delete(room);
            messagingTemplate.convertAndSend("/sub/room/", "room deleted");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/"+key, message);
    }

}
