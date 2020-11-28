//package com.demo.chatdemo.config.kafka;
//
//import com.demo.chatdemo.chat.ChatMessage;
//import com.demo.chatdemo.chat.MessageType;
//import com.demo.chatdemo.chat.UserSession;
//import com.demo.chatdemo.chat.UserSessionRepository;
//import com.demo.chatdemo.config.kafka.ChatProducer;
//import com.demo.chatdemo.room.Room;
//import com.demo.chatdemo.room.RoomRepository;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class ChatConsumer {
//    private final SimpMessageSendingOperations messagingTemplate;
//    private final RoomRepository roomRepository;
//    private final UserSessionRepository userSessionRepository;
//
//    private final Logger logger = LoggerFactory.getLogger(ChatProducer.class);
//    static final String DEFAULT_CHATTING_TOPIC = "chat";
//
//    @KafkaListener(topics = DEFAULT_CHATTING_TOPIC, groupId = "group_id")
//    public void consume(ChatMessage message,
//                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) throws Exception {
//        Room room = roomRepository.findById(key)
//                .orElseThrow(Exception::new);
//        if (MessageType.QUIT.equals(message.getType())) {
//            roomRepository.delete(room);
//            messagingTemplate.convertAndSend("/sub/room/", "room deleted");
//        }
//
//        if (MessageType.ENTER.equals(message.getType())){
//            userSessionRepository.save(UserSession.
//                            builder().
//                            userId(message.getUserId()).
//                            roomId(message.getRoomId()).
//                            build()
//            );
//        }
//
//        if (MessageType.EXIT.equals(message.getType())){
//            userSessionRepository.deleteByUserId(message.getUserId());
//        }
//
//        message.setCount(userSessionRepository.countAllByRoomId(key));
//        room.setCount(message.getCount());
//        roomRepository.save(room);
//        messagingTemplate.convertAndSend("/sub/chat/room/" + key, message);
//    }
//}
