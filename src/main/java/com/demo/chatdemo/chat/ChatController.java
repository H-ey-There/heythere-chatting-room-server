package com.demo.chatdemo.chat;

import com.demo.chatdemo.user.User;
import com.demo.chatdemo.user.UserRepository;
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

    private final SimpMessageSendingOperations messagingTemplate;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) throws Exception {
        Room room = roomRepository.findById(message.getRoomId())
                .orElseThrow(Exception::new);
        User user = userRepository.findById(message.getSender())
                .orElseThrow(Exception::new);;
        System.out.println("room: " + room.getName());
        System.out.println("name: " + user.getName());
        message.setSender(user.getName());
        if (MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        if (MessageType.HOST.equals(message.getType())) {
            roomRepository.delete(room);
        }
        if (MessageType.EXIT.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 퇴장셨습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

}
