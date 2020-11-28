package com.demo.chatdemo;

import com.demo.chatdemo.chat.UserSessionRepository;
import com.demo.chatdemo.room.Room;
import com.demo.chatdemo.room.RoomRepository;
import com.demo.chatdemo.user.User;
import com.demo.chatdemo.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ChatdemoApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;


    @AfterEach
    void deleteAll(){
        userRepository.deleteAll();
        roomRepository.deleteAll();
        userSessionRepository.deleteAll();
    }

    @Test
    void contextLoads() throws Exception {
        Room room = Room.builder().build();
        roomRepository.save(room);
        roomRepository.delete(room);
        System.out.println(roomRepository.findAll());

//        long count = 100L;
//        String roomId = roomRepository.save(Room.builder().count(0L).build()).getRoomId();
//        Room room = roomRepository.findById(roomId).orElseThrow(Exception::new);
//        room.setCount(count);
//        roomRepository.save(room);
//
//        room = roomRepository.findById(roomId).orElseThrow(Exception::new);
//        assertNotNull(room);
//
//        assertEquals(count, room.getCount());
//        System.out.println(roomRepository.findAll());
    }
}
