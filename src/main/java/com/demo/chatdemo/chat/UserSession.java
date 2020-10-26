package com.demo.chatdemo.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("user-session")
@Getter
@Setter
@Builder
@ToString
public class UserSession implements Serializable {
    @Id
    private String userId;
    private String roomId;
}
