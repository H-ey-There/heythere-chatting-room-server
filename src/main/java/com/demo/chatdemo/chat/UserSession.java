package com.demo.chatdemo.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@ToString
public class UserSession{
    @Id
    private String userId;
    private String roomId;
}
