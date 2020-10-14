package com.demo.chatdemo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("users")
@AllArgsConstructor
@Getter @Setter
@Builder
public class User {
    @Id
    private String userId;
    private String name;
}
