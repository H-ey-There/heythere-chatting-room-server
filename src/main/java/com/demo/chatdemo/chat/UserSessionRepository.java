package com.demo.chatdemo.chat;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSessionRepository extends MongoRepository<UserSession, String> {
    void deleteByUserId(String userId);
    Long countAllByRoomId(String roomId);
}
