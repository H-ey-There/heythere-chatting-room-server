package com.demo.chatdemo.room;

import com.demo.chatdemo.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter
@Builder
public class Room {
    @Id
    private String roomId;
    private String name;
    private String host;
    private Long count;
}