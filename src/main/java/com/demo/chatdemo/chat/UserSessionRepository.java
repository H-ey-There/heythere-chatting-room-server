package com.demo.chatdemo.chat;

import org.springframework.data.repository.CrudRepository;

public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}
