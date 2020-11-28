package com.demo.chatdemo.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/chat")
public class RoomController {

    private final RoomRepository roomRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> room() {
       log.info(String.valueOf(roomRepository.findAll()));
       return roomRepository.findAll();
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
    public ResponseEntity<?> roomInfo(@PathVariable String roomId){
//        return chatRoomRepository.findRoomById(roomId);
        if (roomId != null){
            return new ResponseEntity<>(roomRepository.findById(roomId), HttpStatus.OK);
        }
        return null;
    }


}