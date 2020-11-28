package com.demo.chatdemo.chat;

import com.demo.chatdemo.room.Room;
import com.demo.chatdemo.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class ChatController {
    //    private final ChatProducer producer;

    private final SimpMessageSendingOperations messagingTemplate;
    private final RoomRepository roomRepository;
    private final UserSessionRepository userSessionRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) throws Exception {


        Room room = roomRepository.findById(message.getRoomId())
                .orElseThrow(Exception::new);

        System.out.println("message :" + message.getUserId());
        System.out.println("hostId : " + room.getHost());

        if (MessageType.QUIT.equals(message.getType())) {
            roomRepository.delete(room);
            System.out.println("room Deleted");
            messagingTemplate.convertAndSend("/sub/room/", "room deleted");
        }

        if (MessageType.ENTER.equals(message.getType())){
            userSessionRepository.save(UserSession.
                    builder().
                    userId(message.getUserId()).
                    roomId(message.getRoomId()).
                    build()
            );
        }

        if (MessageType.EXIT.equals(message.getType())){
            userSessionRepository.deleteByUserId(message.getUserId());
        }

        message.setCount(
                userSessionRepository.countAllByRoomId(
                        message.getRoomId()));
        room.setCount(message.getCount());
        roomRepository.save(room);
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

}
