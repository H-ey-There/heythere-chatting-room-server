package com.demo.chatdemo.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ChatMessage {
    // 메시지 타입 : 입장, 채팅
    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private Long count;
}

enum MessageType {
    ENTER, TALK, EXIT, HOST
}