package com.demo.chatdemo.chat;

import com.demo.chatdemo.user.User;
import com.demo.chatdemo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) throws Exception {
        System.out.println(message);
        Room room = roomRepository.findById(message.getRoomId())
                .orElseThrow(Exception::new);
        User user = userRepository.findById(message.getSender())
                .orElseThrow(Exception::new);;

        message.setSender(user.getName());

        if (MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            System.out.println(
            userSessionRepository.save(
                    UserSession.builder().
                            userId(user.getUserId()).
                            roomId(room.getRoomId()).
                            build()));
            roomRepository.save(room);
        }

        if (MessageType.HOST.equals(message.getType())) {
            roomRepository.delete(room);
            messagingTemplate.convertAndSend("/sub/room/", "room deleted");
        }
        if (MessageType.EXIT.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 퇴장셨습니다.");
            userSessionRepository.deleteById(
                    user.getUserId()
            );

        }
        message.setCount(
                ((List<UserSession>) userSessionRepository.findAll())
                .stream().filter(session -> session.getRoomId().equals(room.getRoomId()))
                .count()
        );
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

}
