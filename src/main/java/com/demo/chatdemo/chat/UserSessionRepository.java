package com.demo.chatdemo.chat;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}
