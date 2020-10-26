package com.demo.chatdemo.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/chat")
public class RoomController {

//    private final ChatRoomRepository chatRoomRepository;
    private final RoomRepository roomRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> room() {
       log.info(String.valueOf(roomRepository.findAll()));

        return (List<Room>) roomRepository.findAll();
    }

    // 채팅방 생성
    @MessageMapping("/room")
//    @SendTo("/sub/room/")
    public void room(@RequestBody Room room){
        roomRepository.save(room);
        messagingTemplate.convertAndSend("/sub/room/", room);
    }


    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Room roomInfo(@PathVariable String roomId) {
//        return chatRoomRepository.findRoomById(roomId);
        if (roomId != null){
            return roomRepository.findById(roomId).orElse(Room.builder().build());
        }
        return null;
    }

}