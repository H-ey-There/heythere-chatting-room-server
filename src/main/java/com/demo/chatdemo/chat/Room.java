package com.demo.chatdemo.chat;

import com.demo.chatdemo.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@RedisHash("rooms")
@Getter
@Setter
@Builder
public class Room {
    @Id
    private String roomId;
    private String name;
    private String host;
    private List<User> users = new ArrayList<>();

//    public static Room create(String name) {
//        return Room.builder()
//                .roomId(UUID.randomUUID().toString())
//                .name(name)
//                .build();
//    }
}