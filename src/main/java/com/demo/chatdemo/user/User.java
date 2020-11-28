package com.demo.chatdemo.user;

import lombok.*;
import org.springframework.data.annotation.Id;

@ToString
@Getter @Setter
@Builder
public class User {
    @Id
    private String userId;
    private String name;
}
