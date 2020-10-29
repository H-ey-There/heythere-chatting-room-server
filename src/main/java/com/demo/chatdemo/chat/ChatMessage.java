package com.demo.chatdemo.chat;

import lombok.*;

@ToString
@Getter
@Setter
public class ChatMessage {
    // 메시지 타입 : 입장, 채팅
    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String message; // 메시지
    private Long count;

    public ChatMessage message(){
        this.roomId = "hidden";
        return this;
    }
}



enum MessageType {
    QUIT, TALK
}